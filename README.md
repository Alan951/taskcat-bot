# taskcat-bot
Bot de Telegram para la ejecución de tareas basado en plugins.

### TaskCat-Bot service (Linux)
1. Crea el archivo .service en /etc/systemd/system
2. Utiliza el siguiente contenido para generar el archivo:
```bash
[Unit]
Description=TaskCat Bot
After=syslog.target network.target
[Service]
SuccessExitStatus=143
User=root   # change this
Group=root  # change this

Type=forking

ExecStart=/bin/java -jar taskcat-bot.jar
ExecStop=/bin/kill -15 $MAINPID

[Install]
WantedBy=multi-user.target
```
3. Reinicia el daemon-services: `sudo systemctl daemon-reload`
4. Habilita el servicio: `sudo systemctl enable taskcat-bot.service`
5. Verifica que el servicio se encuentre habilitado: `service taskcat-bot status`