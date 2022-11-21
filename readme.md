Aplicação de leitura de arquivos de log e aramazenamento em db.

Essa aplicação foi desenvolvida para obter os logs de pods dos kubernetes e adicionar seu conteúdo no data base Postgrsql.

- Install:

	1.1- Database 
		- Na pasta onde se encontra o arquivo docker-compose.yaml rodar o comando para subir um bco de dados no docker:
	
			docker compose up
			
		- rodar os scripts contidos na pasta sql da aplicação:
			- 1_sequences.sql
			- 2_tables.sql
			- 3_insert_config.sql
			
		Obs: se precisar refazer os objetos, temos o arquivo drop_objects que irá apagar as sequences e tabelas.	
		
		
	1.2- Applicação java

		- mvn clean install
		
		- ir até a pasta target e rodar:
		
			java -jar Logs-0.0.1-SNAPSHOT.jar
			
			
	Obs: no arquivo application.properties temos algumas configurações, como:
		- porta que a app sobe 	
		
		- dados de conexão com o DB
		
		- path onde os arquivos de logs serão salvos
			path.file.logs=/temp/logs/load
		
		- defição do layout do arquivo para dois campos, data e type (INFO, ERROR, WARN, etc)
			#layout
			layout.ini.pos.start.date=0
			layout.end.pos.start.date=24
			layout.ini.pos.type=24
			layout.end.pos.type=30

- end points:
	podemos ver a documentação swagger pelo endereço:
	
		http://localhost:8081/log/
	

	Obs: temos um arquivo Logs.postman_collection.json que contem a collection do postman.