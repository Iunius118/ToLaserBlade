
--------------------------------------------------------------------------------
--  Model converter v3
--      Converts Laser Blade item model from OBJ model
--      This requires that the OBJ model has v, vt, vn, g and f statements
--                        and each f statement has 4 v/vt/vn vertex indices.
--
--      Usage:
--              lua model_cnv.lua <obj_file> <version>
--                  <version>: 0 = 1.15 or earlier, 1 = 1.16 or later
--
--      Example:
--              lua model_cnv.lua laser_blade.obj 1 > output.txt
--------------------------------------------------------------------------------


local hfile = io.open(arg[1], "r")
local ver = tonumber(arg[2]) or 0

if not hfile then
    print("Failed to open file " .. arg[1])
    return
end

local obj = {v = {}, vt = {}, vn = {}, f = {}}
local v3f_tbl = {}
local v2f_tbl = {}
local vector_count = 0
local groups = {}
local group_exists_table = {}
local current_group = ""

local p_statement = "(%a*)%s+(.*)"
local p_3v = "(.*)%s+(.*)%s+(.*)"
local p_2v = "(.*)%s+(.*)"
local p_4ds = "(%d+)/(%d+)/(%d+)%s+(%d+)/(%d+)/(%d+)%s+(%d+)/(%d+)/(%d+)%s+(%d+)/(%d+)/(%d+)"

function getP3F(data)
    local x, y, z = string.gmatch(data, p_3v)()

    if z then
        local p3f = string.format("%sF, %sF, %sF", x, y, z)

        if not v3f_tbl[p3f] then 
            v3f_tbl[p3f] = string.format("v%d", vector_count)
            vector_count = vector_count + 1
        end

        return p3f
    end
end

function getP2F(data)
    local x, y = string.gmatch(data, p_2v)()

    if y then
        local p2f = string.format("%sF, %sF", x, y)

        if not v2f_tbl[p2f] then 
            v2f_tbl[p2f] = string.format("v%d", vector_count)
            vector_count = vector_count + 1
        end

        return p2f
    end
end

for line in hfile:lines() do
    local key, data = string.gmatch(line, p_statement)()

    if key == "v" then
        local pos = getP3F(data)

        if pos then
            table.insert(obj.v, v3f_tbl[pos])
        end

    elseif key == "vn" then
        local normal = getP3F(data)

        if normal then
            table.insert(obj.vn, v3f_tbl[normal])
        end

    elseif key == "vt" then
        local texuv = getP2F(data)

        if texuv then
            table.insert(obj.vt, v2f_tbl[texuv])
        end

    elseif key == "g" then
        current_group = data

        if not group_exists_table[current_group] then
            table.insert(groups, current_group)
            group_exists_table[current_group] = true
        end

    elseif key == "f" then
        local v1, vt1, vn1, v2, vt2, vn2, v3, vt3, vn3, v4, vt4, vn4 = string.gmatch(data, p_4ds)()
        
        if vn4 then
            if not obj.f[current_group] then
                obj.f[current_group] = {}
            end

            table.insert(obj.f[current_group], {
                obj.v[tonumber(v1)], obj.vt[tonumber(vt1)], obj.vn[tonumber(vn1)],
                obj.v[tonumber(v2)], obj.vt[tonumber(vt2)], obj.vn[tonumber(vn2)],
                obj.v[tonumber(v3)], obj.vt[tonumber(vt3)], obj.vn[tonumber(vn3)],
                obj.v[tonumber(v4)], obj.vt[tonumber(vt4)], obj.vn[tonumber(vn4)]
            })
        end

    end
end

hfile:close()

-- Convert group name to CONSTANT_CASE
local convert_case = function (str)
    local ret = ""
    local is_prev_under_bar = true
    local is_prev_num = false

    for i = 1, #str do
        local c = string.sub(str, i, i)
        local is_upper_case = c ~= string.lower(c)
        local is_num = tonumber(c)

        if is_upper_case then
            if is_prev_under_bar then
                ret = ret .. c
            else
                ret = ret .. "_" .. c
            end

            is_prev_under_bar = false
            is_prev_num = false

        elseif is_num then
            if is_prev_under_bar or is_prev_num then
                ret = ret .. c
            else
                ret = ret .. "_" .. c
            end

            is_prev_under_bar = false
            is_prev_num = true

        elseif c == "_" or c == " " then
            ret = ret .. "_"
            is_prev_under_bar = true
            is_prev_num = false

        else
            if is_prev_num then
                ret = ret .. "_" .. c
            else
                ret = ret .. c
            end

            is_prev_under_bar = false
            is_prev_num = false
        end
    end

    return string.upper(ret)
end


print("// " .. arg[1])

-- Print quad lists
for index, group in ipairs(groups) do
    if obj.f[group] then
        print("public static final List<SimpleQuad> " .. convert_case(group) .. "_QUADS;")
    end
end

print()
print("static {")

-- Print 3D vectors (vertex, normal)
for key, value in pairs(v3f_tbl) do
    print(string.format("    Vector3f %s = new Vector3f(%s);", value, key)) -- Using net.minecraft.client.renderer.Vector3f (-1.15.2) or net.minecraft.util.math.vector.Vector3f (1.16.1-)
end

-- Print 2D vectors (uv)
local vec2_name = "Vec2f"

if ver > 0 then
    vec2_name = "Vector2f"  -- Using net.minecraft.util.math.Vec2f (-1.15.2) or net.minecraft.util.math.vector.Vector2f (1.16.1-)
end

for key, value in pairs(v2f_tbl) do
    print(string.format("    %s %s = new %s(%s);", vec2_name, value, vec2_name, key))
end

print("    ImmutableList.Builder<SimpleQuad> builder;")

local color_count = 0
local color = ""
local face_template = "    builder.add(new SimpleQuad(%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s));"

-- Print quads by group
for index, group in ipairs(groups) do
    local value = obj.f[group]

    if value then
        color = "c" .. color_count
        color_count = color_count + 1

        print()
        print("    // " .. group)
        print("    Vector4f " .. color .. " = new Vector4f(1F, 1F, 1F, 1F);")   -- Using net.minecraft.client.renderer.Vector4f (-1.15.2) or net.minecraft.util.math.vector.Vector4f (1.16.1-)
        print("    builder = ImmutableList.builder();")

        for i, face in ipairs(value) do
            print(string.format(face_template,
                face[1], color, face[2], face[3],
                face[4], color, face[5], face[6],
                face[7], color, face[8], face[9],
                face[10], color, face[11], face[12]))
        end

        print("    " .. convert_case(group) .. "_QUADS = builder.build();")
    end
end

print("}")
