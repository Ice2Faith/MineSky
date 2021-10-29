@echo off
echo clean-target-dist

:main
cls
echo 1.clean targets
echo 2.clean dist
echo 3.clean targets and dist
echo else.exit
set RETURN_TAG=main
set /p SEL=
if '%SEL%'=='1' (
	set RETURN_TAG=mainCleanTargetsEnd
	goto cleanTargets
	:mainCleanTargetsEnd
	goto exitApp
)^
else if '%SEL%'=='2' (
	set RETURN_TAG=mainCleanDistEnd
	goto cleanDist
	:mainCleanDistEnd
	goto exitApp
)^
else if '%SEL%'=='3' (
	set RETURN_TAG=mainCleanAllTargtesEnd
	goto cleanTargets
	:mainCleanAllTargtesEnd
	set RETURN_TAG=mainCleanAllDistEnd
	goto cleanDist
	:mainCleanAllDistEnd
	goto exitApp
)^
else (
	goto exitApp
)

:cleanTargets
cls
echo clean targets ...
del /f /s /q ".\i2f-actuator\target"
rd /s /q ".\i2f-actuator\target"

del /f /s /q ".\i2f-actuator-admin\target"
rd /s /q ".\i2f-actuator-admin\target"

del /f /s /q ".\i2f-admin\target"
rd /s /q ".\i2f-admin\target"

del /f /s /q ".\i2f-app\target"
rd /s /q ".\i2f-app\target"

del /f /s /q ".\i2f-batch\target"
rd /s /q ".\i2f-batch\target"

del /f /s /q ".\i2f-common\target"
rd /s /q ".\i2f-common\target"

del /f /s /q ".\i2f-common-api\target"
rd /s /q ".\i2f-common-api\target"

del /f /s /q ".\i2f-config-center\target"
rd /s /q ".\i2f-config-center\target"

del /f /s /q ".\i2f-dao\target"
rd /s /q ".\i2f-dao\target"

del /f /s /q ".\i2f-dubbo\target"
rd /s /q ".\i2f-dubbo\target"

del /f /s /q ".\i2f-dubbo-api\target"
rd /s /q ".\i2f-dubbo-api\target"

del /f /s /q ".\i2f-dubbo-consumer\target"
rd /s /q ".\i2f-dubbo-consumer\target"

del /f /s /q ".\i2f-dubbo-provider\target"
rd /s /q ".\i2f-dubbo-provider\target"

del /f /s /q ".\i2f-elasticsearch\target"
rd /s /q ".\i2f-elasticsearch\target"

del /f /s /q ".\i2f-eureka-server\target"
rd /s /q ".\i2f-eureka-server\target"

del /f /s /q ".\i2f-feign\target"
rd /s /q ".\i2f-feign\target"

del /f /s /q ".\i2f-framework\target"
rd /s /q ".\i2f-framework\target"

del /f /s /q ".\i2f-model\target"
rd /s /q ".\i2f-model\target"

del /f /s /q ".\i2f-mq-rabbit\target"
rd /s /q ".\i2f-mq-rabbit\target"

del /f /s /q ".\i2f-quartz\target"
rd /s /q ".\i2f-quartz\target"

del /f /s /q ".\i2f-redis\target"
rd /s /q ".\i2f-redis\target"

del /f /s /q ".\i2f-security\target"
rd /s /q ".\i2f-security\target"

del /f /s /q ".\i2f-security-core\target"
rd /s /q ".\i2f-security-core\target"

del /f /s /q ".\i2f-service\target"
rd /s /q ".\i2f-service\target"

del /f /s /q ".\i2f-service-user\target"
rd /s /q ".\i2f-service-user\target"

del /f /s /q ".\i2f-swagger\target"
rd /s /q ".\i2f-swagger\target"

del /f /s /q ".\i2f-zipkin\target"
rd /s /q ".\i2f-zipkin\target"

del /f /s /q ".\i2f-zookeeper\target"
rd /s /q ".\i2f-zookeeper\target"

del /f /s /q ".\i2f-zuul\target"
rd /s /q ".\i2f-zuul\target"

echo clean targets done.
goto %RETURN_TAG%

:cleanDist
cls
echo clean dist...
del /f /s /q ".\i2f-admin-ui\dist"
rd /s /q ".\i2f-admin-ui\dist"

del /f /s /q ".\i2f-app-ui\dist"
rd /s /q ".\i2f-app-ui\dist"
echo clean dist done.
goto %RETURN_TAG%

:exitApp
echo done
exit
