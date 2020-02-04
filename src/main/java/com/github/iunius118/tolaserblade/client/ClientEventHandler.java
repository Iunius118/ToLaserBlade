package com.github.iunius118.tolaserblade.client;

import com.github.iunius118.tolaserblade.ToLaserBlade;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.VersionChecker;
import net.minecraftforge.fml.VersionChecker.CheckResult;
import net.minecraftforge.fml.VersionChecker.Status;


public class ClientEventHandler {
    public static void checkUpdate() {
        // Check update and Notify client
        CheckResult result = VersionChecker.getResult(ModList.get().getModFileById(ToLaserBlade.MOD_ID).getMods().get(0));
        Status status = result.status;

        if (status == Status.PENDING || result.target == null) {
            // Failed to get update information
            return;
        }

        if (status == Status.OUTDATED || status == Status.BETA_OUTDATED) {
            ITextComponent modNameHighlighted = new StringTextComponent(ToLaserBlade.MOD_NAME);
            modNameHighlighted.getStyle().setColor(TextFormatting.YELLOW);

            ITextComponent newVersionHighlighted = new StringTextComponent(result.target.toString());
            newVersionHighlighted.getStyle().setColor(TextFormatting.YELLOW);

            ITextComponent message = new TranslationTextComponent("tolaserblade.update.newversion", modNameHighlighted).appendText(": ")
                    .appendSibling(newVersionHighlighted);
            message.getStyle().setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, result.url));

            Minecraft.getInstance().ingameGUI.getChatGUI().printChatMessage(message);
        }
    }
}
