#!/usr/bin/env python3
#
# Copyright 2009-2019 TMD-Maker Project <https://www.tmdmaker.org>
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#

# -*- coding: utf-8 -*-

# sphinx-maven-plugin と python3 の両方で動かすため
import sys
if sys.version_info[0] == 2:
   reload(sys)
   sys.setdefaultencoding("utf-8")

extensions = ['sphinx.ext.todo']

templates_path = ['_templates']

source_suffix = ['.rst', '.md']

source_encoding = 'utf-8-sig'

master_doc = 'index'

project = 'TMD-Maker'
copyright = '2019, TMD-Maker Project'
author = 'TMD-Maker Project'
version = '0.9'
release = '0.9.0-SNAPSHOT'

language = 'ja'

exclude_patterns = ['_build', 'Thumbs.db', '.DS_Store']

pygments_style = 'sphinx'
todo_include_todos = True

html_theme = 'bizstyle'
html_static_path = ['_static']
