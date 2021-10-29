@echo off
echo build ui

start cmd /c "title build-admin-ui && cd i2f-admin-ui && rd /s /q dist && npm run build && cd dist && ..\..\bin\zip.exe -q -r .\dist.zip *.* && exit"

start cmd /c "title build-app-ui && cd i2f-app-ui && rd /s /q dist && npm run build && cd dist && ..\..\bin\zip.exe -q -r .\dist.zip *.* && exit"

