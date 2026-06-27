package com.github.iunius118.tolaserblade.menu;

import com.github.iunius118.tolaserblade.item.crafting.BlueprintRecipe;
import com.github.iunius118.tolaserblade.item.crafting.BlurprintRecipeInput;
import com.github.iunius118.tolaserblade.item.crafting.CraftingRecipe;
import com.github.iunius118.tolaserblade.item.crafting.ModRecipeTypes;
import com.github.iunius118.tolaserblade.mixin.ResultSlotAccessor;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.Optional;

public class BlueprintMenu extends AbstractContainerMenu {
    public static final int INPUT_COUNT = 4;
    // Player inventory area
    public static final int PLAYER_INV_X = 8;
    public static final int PLAYER_INV_Y = 84;

    // Slot index boundaries
    private static final int RESULT_SLOT      = 0;
    private static final int INPUT_FIRST      = 1;
    private static final int INPUT_LAST       = INPUT_FIRST + INPUT_COUNT;  // exclusive = 5
    private static final int PLAYER_INV_FIRST = INPUT_LAST;             // 5
    private static final int PLAYER_INV_LAST  = PLAYER_INV_FIRST + 27;  // 32
    private static final int HOTBAR_FIRST     = PLAYER_INV_LAST;        // 32
    private static final int HOTBAR_LAST      = HOTBAR_FIRST + 9;       // 41
    // Coordinates of each UI element (offset from top left of GUI)
    private static final int INPUT_SLOT_X  = 22;
    private static final int INPUT_SLOT_Y  = 35;
    private static final int RESULT_SLOT_X = 134;
    private static final int RESULT_SLOT_Y = 35;

    private static final List<RecipeType<? extends BlueprintRecipe>> RECIPE_SEARCH_ORDER = List.of(
            ModRecipeTypes.CRAFTING,
            ModRecipeTypes.ENCHANTMENT,
            ModRecipeTypes.COLORING,
            ModRecipeTypes.BLENDING,
            ModRecipeTypes.REMODEL
    );

    private final SimpleContainer inputContainer;
    private final ResultContainer resultContainer;
    private final ContainerLevelAccess access;
    private final Player player;
    private final Level level;

    private RecipeHolder<? extends BlueprintRecipe> selectedRecipe;

    // Client-side constructor
    public BlueprintMenu(int containerId, Inventory inventory) {
        this(containerId, inventory, ContainerLevelAccess.NULL);
    }

    // Server-side constructor
    public BlueprintMenu(int containerId, Inventory inventory, ContainerLevelAccess access) {
        super(ModMenuTypes.BLUEPRINT, containerId);

        this.inputContainer = new SimpleContainer(INPUT_COUNT) {

            @Override
            public void setChanged() {
                super.setChanged();
                slotsChanged(this);
            }
        };
        this.resultContainer = new ResultContainer();
        this.access = access;
        this.player = inventory.player;
        this.level = player.level();

        // Result slot
        CraftingContainer craftSlots = new TransientCraftingContainer(this, INPUT_COUNT, 1) {

            @Override
            public List<ItemStack> getItems() {
                return BlueprintMenu.this.inputContainer.getItems();
            }
        };
        this.addSlot(new ResultSlot(this.player, craftSlots, this.resultContainer, 0, RESULT_SLOT_X, RESULT_SLOT_Y) {

            @Override
            protected void checkTakeAchievements(ItemStack carried) {
                if (!BlueprintMenu.this.isCraftingRecipe()) {
                    ((ResultSlotAccessor)(Object) this).setRemoveCount(0);
                }

                super.checkTakeAchievements(carried);
            }

            @Override
            public void onTake(Player player, ItemStack carried) {
                boolean shouldConsumeIngredients = BlueprintMenu.this.shouldConsumeIngredients();
                this.checkTakeAchievements(carried);

                for (int i = 0; i < BlueprintMenu.this.inputContainer.getContainerSize(); i++) {
                    if (i == 1 && !shouldConsumeIngredients) {
                        // Don't consume ingredients
                        break;
                    }

                    // Consume ingredients
                    var itemStack = BlueprintMenu.this.inputContainer.getItem(i);

                    if (!itemStack.isEmpty()) {
                        itemStack.shrink(1);
                        BlueprintMenu.this.inputContainer.setItem(i, itemStack);
                    }
                }
            }
        });

        // Input slots
        for (int i = 0; i < INPUT_COUNT; i++) {
            this.addSlot(new Slot(this.inputContainer, i, INPUT_SLOT_X + i * 18, INPUT_SLOT_Y));
        }

        this.addStandardInventorySlots(inventory, PLAYER_INV_X, PLAYER_INV_Y);

    }

    private boolean isCraftingRecipe() {
        if (selectedRecipe == null) {
            return false;
        }

        return selectedRecipe.value() instanceof CraftingRecipe;
    }

    private boolean shouldConsumeIngredients() {
        if (selectedRecipe == null) {
            return false;
        }

        return selectedRecipe.value().shouldConsumeIngredient();
    }

    @Override
    public void slotsChanged(Container container) {
        super.slotsChanged(container);

        if (level != null && !level.isClientSide()) {
            updateResult();
        }
    }

    private void updateResult() {
        BlurprintRecipeInput input = BlurprintRecipeInput.of(inputContainer);
        var recipe = findRecipe(input);

        if (recipe.isPresent()) {
            selectedRecipe = recipe.get();
            ItemStack result = selectedRecipe.value().assemble(input);
            resultContainer.setItem(0, result);
        } else {
            selectedRecipe = null;
            resultContainer.setItem(0, ItemStack.EMPTY);
        }

        broadcastChanges();
    }

    private Optional<? extends RecipeHolder<? extends BlueprintRecipe>> findRecipe(
            BlurprintRecipeInput input) {
        if (level instanceof ServerLevel serverLevel) {
            RecipeManager recipeManager = serverLevel.recipeAccess();

            for (var type : RECIPE_SEARCH_ORDER) {
                var recipe = recipeManager.getRecipeFor(type, input, serverLevel);

                if (recipe.isPresent()) {
                    return recipe;
                }
            }
        }

        return Optional.empty();
    }

    @Override
    public ItemStack quickMoveStack(Player player, int slotIndex) {
        Slot slot = this.slots.get(slotIndex);

        if (!slot.hasItem()) {
            return ItemStack.EMPTY;
        }

        ItemStack stack = slot.getItem();
        ItemStack clicked = stack.copy();

        if (slotIndex == RESULT_SLOT) {
            // Output slot -> player inventory
            stack.getItem().onCraftedBy(stack, player);

            if (!moveItemStackTo(stack, PLAYER_INV_FIRST, HOTBAR_LAST, true)) {
                return ItemStack.EMPTY;
            }

            slot.onQuickCraft(stack, clicked);
        } else if (slotIndex < INPUT_LAST) {
            // Input slot -> player inventory
            if (!moveItemStackTo(stack, PLAYER_INV_FIRST, HOTBAR_LAST, false)) {
                return ItemStack.EMPTY;
            }
        } else if (slotIndex < HOTBAR_LAST) {
            // Player inventory -> input slot
            if (!this.moveItemStackTo(stack, INPUT_FIRST, INPUT_LAST, false)) {
                if (slotIndex < PLAYER_INV_LAST) {
                    // Player inventory -> player hotbar
                    if (!this.moveItemStackTo(stack, HOTBAR_FIRST, HOTBAR_LAST, false)) {
                        return ItemStack.EMPTY;
                    }
                // Player hotbar -> player inventory
                } else if (!this.moveItemStackTo(stack, PLAYER_INV_FIRST, PLAYER_INV_LAST, false)) {
                    return ItemStack.EMPTY;
                }
            }
        }

        if (stack.isEmpty()) {
            slot.setByPlayer(ItemStack.EMPTY);
        } else {
            slot.setChanged();
        }

        if (stack.getCount() == clicked.getCount()) {
            return ItemStack.EMPTY;
        }

        slot.onTake(player, stack);

        if (slotIndex == RESULT_SLOT) {
            player.drop(stack, false);
        }

        return clicked;
    }


    @Override
    public boolean stillValid(Player player) {
        return access.evaluate((level, pos) -> player.isWithinBlockInteractionRange(pos, 4.0), true);
    }

    @Override
    public void removed(Player player) {
        super.removed(player);
        clearContainer(player, inputContainer);
    }
}
