# boot-sftp
This is the project to demostrate publish and delete files from SFTP using Spring boot + Spring Web + Spring Integration + SFTP

## SFTP Connection Details:
Update following properties in application.properties.
- sftpserver.host=
- sftpserver.username=
- sftpserver.password=

## REST Endpoints
- htpp://localhost:9200/bootsftp/upload
```
Request Method: POST
PayLoad:
[
	{
		"orderId":"243",
		"productName":"ABC",
		"customerAddress":"PA,USA",
		"customerName":"Jan"
	},{
		"orderId":"24355",
		"productName":"AB33C",
		"customerAddress":"PA,US33A",
		"customerName":"Jan33"
	}
]
```
- http://localhost:9200/bootsftp/delete
```
Request Method: DELETE
```
