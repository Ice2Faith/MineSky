echo del backup ...
del /s /f /q .\deploy-bak
rd /s /q .\deploy-bak
echo do backup ...
ren .\deploy .\deploy-bak
echo begin get deploy files ...
md .\deploy
move .\i2f-actuator-admin\target\i2f-actuator-admin.jar .\deploy\i2f-actuator-admin.jar
move .\i2f-admin\target\i2f-admin.jar .\deploy\i2f-admin.jar
move .\i2f-app\target\i2f-app.jar .\deploy\i2f-app.jar
move .\i2f-config-center\target\i2f-config-center.jar .\deploy\i2f-config-center.jar
move .\i2f-eureka-server\target\i2f-eureka-server.jar .\deploy\i2f-eureka-server.jar
move .\i2f-zuul\target\i2f-zuul.jar .\deploy\i2f-zuul.jar
move .\i2f-admin-ui\dist\dist.zip .\deploy\i2f-admin-ui.zip
move .\i2f-app-ui\dist\dist.zip .\deploy\i2f-app-ui.zip
echo done.
