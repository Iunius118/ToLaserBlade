##########################################################
#   Obj to JSON for ToLaserBlade v6 for JSON Model v1    #
##########################################################

'''
# python obj2json_tlb6.py {obj_path} [-m {model_id}] [-r {renderer_id=0}] [-s {model_rescale=1.0}]
# Valid model ID is an integer >= 0
$ python obj2json_tlb6.py test.obj -m 0

Obj requires normal vector ('nv') data

Objects of the model are defined by group names in the obj
# Group name for object of geometry data
# object_name:type:part:state
g test_1:add:blade_in:on
# Generates {"type": "add", "part": "blade_in", "state": "on", ...}
f 1/1/1 1/1/1 1/1/1 1/1/1
#
# Group name for objects of function and geometry data
# object_name:type:part:state:function_name:function_state
g test_2:flat:grip:off:rotate:on
# Generates {"type": "function", "name": "rotate", "state": "on"}, {"type": "flat", "part": "grip", "state": "off", ...}
f 1/1/1 1/1/1 1/1/1 1/1/1
'''

import argparse
import os
import json
import sys

def _main():
    args = _parse_arg()

    # Change working dir to obj file's one
    new_current_dir = os.path.dirname(args.path)
    if len(new_current_dir) > 0:
        os.chdir(new_current_dir)

    obj = {'type': 'tolaserblade:model_v1'}
    obj['id'] = args.model

    if args.renderer > 0:
        obj['renderer_id'] = args.renderer

    obj = {**obj, **load_obj(args.path, args.scale)}
    #print(obj)

    _save_json(args.path, obj)
    print("Done.")

def _parse_arg():
    parser = argparse.ArgumentParser(description='Convert obj file to json file for ToLaserBlade v6 by Iunius118.')
    parser.add_argument('path', help='path to an obj file to input')
    parser.add_argument('-m', '--model', type=int, default=-1, help='model ID')
    parser.add_argument('-r', '--renderer', type=int, default=0, help='renderer ID')
    parser.add_argument('-s', '--scale', type=float, default=1, help='ratio to resize')
    return parser.parse_args()

def load_obj(path, scale):
    objects = []
    vertices = []
    vertex_colors = []
    normals = []
    faces = []

    object = {}
    object_from = 0
    mtl_data = {}
    current_mtllib = ''
    current_mtl = ''
    vertex_color_map = {}
    vertex_color_index = 0

    lines = []
    with open(path, 'r') as f:
        lines = f.readlines()

    for line in lines:
        vals = line.split()

        if len(vals) <= 1:
            continue
        if vals[0][0] == '#':
            continue
        if (vals[0] == 'o' or vals[0] == 'g') and len(vals) >= 2:
            size_faces = len(faces)
            if size_faces > object_from:
                # Set count of faces to prev object and add the object to objects 
                object['size'] = size_faces - object_from
                objects.append(object)
                # Creat next object
                object = {}
            object_from = len(faces)
            elements = vals[1].split(':')
            if len(elements) > 1 and len(elements[1]) > 0:
                # Object type
                object['type'] = elements[1]
            if len(elements) > 2 and len(elements[2]) > 0:
                # Object part
                object['part'] = elements[2]
            if len(elements) > 3 and len(elements[3]) > 0:
                # Object state to render
                object['state'] = elements[3]
            if len(elements) > 4 and len(elements[4]) > 0:
                # Object function
                # Function name
                object_fx = {'type': 'function'}
                object_fx['name'] = elements[4]
                if len(elements) > 5 and len(elements[5]) > 0:
                    # Function state to call
                    object_fx['state'] = elements[5]
                # Add function object to objects before the object
                objects.append(object_fx)
            object['from'] = object_from
        if vals[0] == 'v' and len(vals) >= 4:
            # Resize by given scale
            vertex = [n * scale for n in map(float, vals[1:4])]
            vertices.append(vertex)
        if vals[0] == 'vn' and len(vals) >= 4:
            normal = list(map(float, vals[1:4]))
            normals.append(normal)
        if vals[0] == 'mtllib' and len(vals) >= 2:
            current_mtllib = vals[1]
            mtl_data = _load_mtl(current_mtllib)
            # print(mtl_data)
        if vals[0] == 'usemtl' and len(vals) >= 2:
            current_mtl = vals[1]
            key = (current_mtllib, current_mtl)
            if key in vertex_color_map:
                vertex_color_index = vertex_color_map[key]
            elif current_mtl in mtl_data:
                vertex_color_index = len(vertex_colors)
                vertex_colors.append(mtl_data[current_mtl])
                vertex_color_map[key] = vertex_color_index
        if vals[0] == 'f' and 4 <= len(vals) <= 5:
            face = []
            for f in vals[1:]:
                vs = f.split('/')
                if len(vs) >= 3:
                    face.append(int(vs[0]) - 1)
                    if len(vertex_colors) == 0:
                        vertex_colors.append([1.0,1.0,1.0,1.0])
                        vertex_color_map[('', '')] = 0
                    face.append(vertex_color_index)
                    face.append(int(vs[2]) - 1)
                else:
                    # When there are no nvs.
                    print("Elemants of 'nv' are requierd for each vertex in faces.")
                    sys.exit(1)
            if len(face) == 9:
                # Fix 3 vertices to 4 vertices
                face.extend(face[6:9])

            faces.append(face)

    # Set count of faces to last object and add the object to objects 
    size_faces = len(faces)
    if size_faces > object_from:
        object['size'] = size_faces - object_from
        objects.append(object)

    # Check normal vectors
    if len(faces) > 0 and len(normals) == 0:
        # When there are no nvs.
        print("Statements of 'nv' are requierd.")
        sys.exit(1)

    # Remove duplicate vertices
    vertex_to_fixed_index = {}
    v_index_replace = []
    next_index = 0
    fixed_vertices = []
    for v in range(len(vertices)):
        t_vtx = tuple(vertices[v])
        if t_vtx in vertex_to_fixed_index:
            v_index_replace.append(vertex_to_fixed_index[t_vtx])
        else:
            vertex_to_fixed_index[t_vtx] = next_index
            v_index_replace.append(next_index)
            fixed_vertices.append(vertices[v])
            next_index += 1
    for face in faces:
        face[0] = v_index_replace[face[0]]
        face[3] = v_index_replace[face[3]]
        face[6] = v_index_replace[face[6]]
        face[9] = v_index_replace[face[9]]
    vertices = fixed_vertices

    # Process double state objects
    fixed_objects = []

    for object in objects:
        if 'state' in object and '/' in object['state']:
            object_1 = {}
            object_2 = {}
            if 'type' in object:
                if '/' in object['type']:
                    types = object['type'].split('/')
                    if len(types[0]) > 0:
                        object_1['type'] = types[0]
                    if len(types[1]) > 0:
                        object_2['type'] = types[1]
                else:
                    object_1['type'] = object_2['type'] = object['type']
            if 'part' in object:
                if '/' in object['part']:
                    parts = object['part'].split('/')
                    if len(parts[0]) > 0:
                        object_1['part'] = parts[0]
                    if len(parts[1]) > 0:
                        object_2['part'] = parts[1]
                else:
                    object_1['part'] = object_2['part'] = object['part']
            if 'name' in object:
                if '/' in object['name']:
                    names = object['name'].split('/')
                    if len(names[0]) > 0:
                        object_1['name'] = names[0]
                    if len(names[1]) > 0:
                        object_2['name'] = names[1]
                else:
                    object_1['name'] = object_2['name'] = object['name']
            states = object['state'].split('/')
            if len(states[0]) > 0:
                object_1['state'] = states[0]
            if len(states[1]) > 0:
                object_2['state'] = states[1]
            if object['type'] != 'function':
                object_1['from'] = object_2['from'] = object['from']
                object_1['size'] = object_2['size'] = object['size']
            fixed_objects.append(object_1)
            fixed_objects.append(object_2)
        else:
             fixed_objects.append(object)
    objects = fixed_objects

    return {'objects': objects, 'vertices': vertices, 'colors': vertex_colors, 'normals': normals, 'faces': faces}

def _load_mtl(path):
    mtl_data = {}
    current_mtl = ''
    current_color = []

    for line in open(path, 'r'):
        vals = line.split()

        if len(vals) <= 1:
            continue
        if vals[0][0] == '#':
            continue
        if vals[0] == 'newmtl' and len(vals) >= 2:
            if len(current_mtl) > 0 and len(current_color) == 4:
                mtl_data[current_mtl] = current_color
            current_mtl = vals[1]
            current_color = [1.0, 1.0, 1.0, 1.0]
        if vals[0] == 'Kd' and len(vals) >= 4:
            if len(current_color) == 4:
                kb = list(map(float, vals[1:4]))
                for n in range(3):
                    current_color[n] = kb[n]
        if vals[0] == 'd' and len(vals) >= 2:
            if len(current_color) == 4:
                current_color[3] = float(vals[1])
        if vals[0] == 'Tr' and len(vals) >= 2:
            if len(current_color) == 4:
                current_color[3] = float(1.0 - vals[1])

    if len(current_mtl) > 0 and len(current_color) == 4:
        mtl_data[current_mtl] = current_color

    return mtl_data

def _save_json(path, obj):
    with open(path + '.json', 'w') as f:
        json.dump(obj, f, separators=(',', ':'))

if __name__ == "__main__":
    _main()
