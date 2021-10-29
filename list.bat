@echo off
echo list

set LIST_FILE=list.txt

echo list processing...

echo update datetime : %date% %time% > %LIST_FILE%
echo ------------------------------- >> %LIST_FILE%
dir /b >> %LIST_FILE%
echo ------------------------------- >> %LIST_FILE%
dir /b /s >> %LIST_FILE%

set TREE_LIST_FILE=tree_%LIST_FILE%
echo update datetime : %date% %time% > %TREE_LIST_FILE%
echo ------------------------------- >> %TREE_LIST_FILE%
dir /b >> %TREE_LIST_FILE%
echo ------------------------------- >> %TREE_LIST_FILE%
tree /A /F >> %TREE_LIST_FILE%