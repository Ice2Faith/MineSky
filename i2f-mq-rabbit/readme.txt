下载安装RabbitMq:
    1.安装ErLang环境
        下载地址：
            http://www.erlang.org/downloads
        安装并配置环境变量：
            ERLANG_HOME
                C:\安装路径
            Path
                %ERLANG_HOME%\bin
        检查是否安装成功：
            cmd
                erl
    2.安装RabbitMq
        下载地址：
            http://www.rabbitmq.com/download.html
        安装，配置管理项
            进入安装目录/sbin
                rabbitmq-plugins enable rabbitmq_management
            检查是否启动
                rabbitmqctl status
        [可选]运行server,安装上默认就启动了，所以一般是启动失败
            rabbitmq-server.bat
        访问可视化管理页面：
            http://localhost:15672
            默认账号密码都是：guest
