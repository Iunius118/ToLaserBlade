
--------------------------------------------------------------------------------
--  Model converter
--      Converts Laser Blade item model from OBJ model
--      This requires that the OBJ model has v, vt, vn, g and f statements
--                        and each f statement has 4 v/vt/vn vertex indices.
--
--      Usage:
--              lua model_cnv.lua laser_blade.obj > output.txt
--------------------------------------------------------------------------------


local hfile = io.open(arg[1], "r")

if not hfile then
    print("Failed to open file " .. arg[1])
    return
end

local obj = {v = {}, vt = {}, vn = {}, f = {}}
local v3f_tbl = {}
local v2f_tbl = {}
local vector_count = 0
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

for key, value in pairs(v3f_tbl) do
    print(string.format("private static final Vector3f %s = new Vector3f(%s);", value, key))
end

for key, value in pairs(v2f_tbl) do
    print(string.format("private static final Vec2f %s = new Vec2f(%s);", value, key))
end

print()

local color_count = 0
local color = ""
local face_template = "    builder.add(new FaceData(%s, c, %s, %s, %s, c, %s, %s, %s, c, %s, %s, %s, c, %s, %s));"

for key, value in pairs(obj.f) do
    color = "c" .. color_count
    color_count = color_count + 1

    print("public static final List<FaceData> " .. key .. "Faces = get" .. key .. "Faces();\n")

    print("public static List<FaceData> get" .. key .. "Faces() {")
    print("    Vector4f c = new Vector4f(1F, 1F, 1F, 1F);\n ")
    print("    ImmutableList.Builder<FaceData> builder = ImmutableList.builder();")

    for i, face in ipairs(value) do
        print(string.format(face_template,
            face[1], face[2], face[3],
            face[4], face[5], face[6],
            face[7], face[8], face[9],
            face[10], face[11], face[12]))
    end

    print("    return builder.build();\n}\n")
end
