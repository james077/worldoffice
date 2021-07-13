# worldoffice
Back carrito de compras

Descripción:
El proyecto está construido con spring boot 5. Contiene un job batch que carga los productos agregados en un archivo .csv y los administra a través de servicios REST.

Acceso a BD:
H2: http://localhost:7050/worldofficeapi/h2/
usuario:sa
contraseña:

Documentación
El proyecto posee documentación generada con swagger la cual se puede acceder una vez desplegado el artefacto
swagger: http://localhost:7050/worldofficeapi/

Colección Postman
se agrega colección con las peticiones postman en el archivo src\main\resources

Notas:
El Job se ejecuta al realizar la petición get localhost:7050/worldofficeapi/products-job