# -*- coding:utf-8 -*-

import os
from os import path


def subdir_list(dirname):
    """获取目录下所有子目录名
    @param dirname: str 目录的完整路径
    @return: list(str) 所有子目录完整路径组成的列表
    """
    return list(filter(os.path.isdir,
                       map(lambda filename: os.path.join(dirname, filename),
                           os.listdir(dirname))))


def file_list(dirname, ext='.java'):
    """获取目录下所有特定后缀的文件
    @param dirname: str 目录的完整路径
    @param ext: str 后缀名, 以点号开头
    @return: list(str) 所有子文件名(不包含路径)组成的列表
    """
    return list(filter(
        lambda filename: os.path.splitext(filename)[1] == ext,
        os.listdir(dirname)))


def recursive_files(result, path, ext='.java'):
    parents = os.listdir(path)
    for parent in parents:
        child = os.path.join(path, parent)
        if os.path.isdir(child):
            recursive_files(result, child)
        else:
            basename, e = os.path.splitext(os.path.basename(child))
            if e == ext:
                result.append(child)


def head_license(file):
    template = """/*
 * Copyright 2019 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */"""

    with open(file, "r+") as f:
        old = f.read()
        f.seek(0)
        f.write(template)
        f.write("\n")
        f.write(old)


if __name__ == '__main__':
    script_path = os.path.realpath(__file__)

    base_path = path.dirname(script_path)

    result = []
    recursive_files(result, base_path)
    for dir in result:
        head_license(dir)
