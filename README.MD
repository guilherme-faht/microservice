# MICROSERVICE ARQUITETURE

## COMPONENTS

- Service Discovery (folder: registry)
- API Gateway (folder: apigateway)
- Order service (folder: order)
- Transport service (folder: transport)

## REQUISITES

- Docker 2.3.0.5
- JAVA 11
- Gradle

## HOW TO INSTALL

1 - Locate the docker-compose.yml file. Open the terminal and type "docker-compose up";

2 - Open your browser and access the address "http://localhost:16543/browser/";

3 - Pgadmin will open. 

Username: gurus.gui@gmail.com
Password: postgres

4 - Create a server with following configuration:

General    -> Name: localhost
Connection -> Hostname/address: postgres  
Connection -> Port: 5432 
Connection -> Username: postgres 

5 - Create a database "order"

6 - Create a database "transport"

7 - Start the services in the following order 

- Service Discovery;
- API Gateway;
- Order service;
- Transport service

8 - Open the powershell and execute the following commands to test the services.


To create a new order
```
$headers=@{}
$headers.Add("content-type", "application/json")
$response = Invoke-WebRequest -Uri 'http://localhost:8080/api/order-endpoint/order/insert' -Method POST -Headers $headers -ContentType 'application/json' -Body '{
	"clientId": "bb8e800c-cb85-4409-ad1a-bc6a0060396c",
	"orderProducts": [
		{
			"productId": "c5650dc5-d248-4da1-ba19-5836256be174"
		},
		{
			"productId": "c5650dc5-d248-4da1-ba19-5836256be174"
		},
		{
			"productId": "c5650dc5-d248-4da1-ba19-5836256be174"
		},
		{
			"productId": "c5650dc5-d248-4da1-ba19-5836256be174"
		},
		{
			"productId": "c5650dc5-d248-4da1-ba19-5836256be174"
		}
	]
}'
echo $response;
```


```
$transportId = 'f4650544-018b-4761-8dfc-14f646b357ed'
$response = Invoke-WebRequest -Uri "http://localhost:8080/api/transport-endpoint/transport/$($transportId)" -Method GET 
echo $response
```




