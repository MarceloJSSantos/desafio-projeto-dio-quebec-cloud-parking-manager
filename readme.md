### Gerenciador de Estacionamento em Nuvem

Desafio de projeto final do Bootcamp Quebec Digital da DIO

------------------------

#### Publicação no Hiroku
url: [http://dio-quebec-parking-manage.herokuapp.com](http://dio-quebec-parking-manage.herokuapp.com)

1) Incluído o arquivo ``system.properties`` para compatibilidade c/ Java 11

#### Uso de Design Patterns - DTO (Data Transfer Object)

É um padrão de arquitetura de objetos que agregam e encapsulam dados para transferência.

O DTO não possui qualquer tipo de comportamento. A sua função é obter e armazenar dados.

O DTO é bastante utilizado também quando não queremos expor todos os dados da nossa camada de persistência mas precisamos exibir ao nosso cliente estes mesmos dados.

#### APIs Documentadas com Swagger (bibliote ca OpenAPI)

Para isso bastou incluir no ``pom.xml`` as linhas:
  ````xml
    <dependency>
        <groupId>org.springdoc</groupId>
        <artifactId>springdoc-openapi-ui</artifactId>
        <version>1.6.12</version>
    </dependency>
 ````

Em subistituição ao Swagger 2 usamos as anotações do swagger 3 (já está incluído na springdoc-openapi-uidependência). O pacote para anotações do swagger 3 é io.swagger.v3.oas.annotations.

- ``@ApiParam`` -> ``@Parameter``
- ``@ApiOperation`` -> ``@Operation``
- ``@Api`` -> ``@Tag``
- ``@ApiImplicitParams`` -> ``@Parameters``
- ``@ApiImplicitParam`` -> ``@Parameter``
- ``@ApiIgnore`` -> ``@Parameter(hidden = true)`` ou ``@Operation(hidden = true)`` ou ``@Hidden``
- ``@ApiModel`` -> ``@Schema``
- ``@ApiModelProperty`` -> ``@Schema``

Ou em vez de usar **ApiInfo** usamos:
````java
    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("SpringShop API")
                .description("Spring shop sample application")
                .version("v0.0.1")
                .license(new License().name("Apache 2.0").url("http://springdoc.org")))
                .externalDocs(new ExternalDocumentation()
                .description("SpringShop Wiki Documentation")
                .url("https://springshop.wiki.github.org/docs"));
    }
````

Se tiver apenas um Docket (Swagger 2) podemos adicionar as propriedades ao seu ``application.properties`` para o Swagger 3:
````properties
 springdoc.packagesToScan=package1, package2
 springdoc.pathsToMatch=/v1, /api/balance/**
````
Ou 
**Antes**
````java
    @Bean
    public Docket publicApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("org.github.springshop.web.public"))
                .paths(PathSelectors.regex("/public.*"))
                .build()
                .groupName("springshop-public")
                .apiInfo(apiInfo());
    }

    @Bean
    public Docket adminApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("org.github.springshop.web.admin"))
                .paths(PathSelectors.regex("/admin.*"))
                .build()
                .groupName("springshop-admin")
                .apiInfo(apiInfo());
    }
````

**Agora**
````java
    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .setGroup("springshop-public")
                .pathsToMatch("/public/**")
                .build();
    }

    @Bean
    public GroupedOpenApi adminApi() {
        return GroupedOpenApi.builder()
                .setGroup("springshop-admin")
                .pathsToMatch("/admin/**")
                .build();
    }
````

[Referência: Migrando do Springfox Swagger 2 para o Springdoc Open API](https://stackoverflow.com/questions/59291371/migrating-from-springfox-swagger-2-to-springdoc-open-api)


#### Tratamento de Exceções para retorno de mensagem mais amigável

- Para isso foi criada a exception ``EstacionamentoNotFoundException`` com mensagem mais adequada;
- Adequado o fluxo no método ``findById`` da classe de serviço ``ÈstacionamentoService``;
- Incluída a configuração no ``application.properties`` para apresentação de mensagem mais adequada
  ````properties
  server.error.include-exception=false
  server.error.include-stacktrace=never
  server.error.include-message=always
  ````

#### Incluído testes de integração

- Para isso foi incluída a biblioteca do Rest Assured
  ````xml
  <dependency>
    <groupId>io.rest-assured</groupId>
    <artifactId>rest-assured</artifactId>
    <scope>test</scope>
  <dependency>
  ````

#### Implementando a persistência de dados com JPA

- Acrescentadas as linhas abaixo no ``pom.xml``
  ````xml
  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
  </dependency>

  <dependency>
    <groupId>org.postgresql</groupId>
    <artifactId>postgresql</artifactId>
  </dependency>
  ````
  **nota: Como não possuia o Docker na máquina do projeto, adequei usando o bd H2 com persistência em arquivo**
- Incluída a anotação ``@Entity`` na classe modelo (Estacionamento) e as anotações ncessárias nos atributos;
- Criada a interface ``Repository``;
- Refatorada a ``Service`` para usar a ``Repository``;
- Incluída a ``@Transations`` nos métodos na ``Service`` para o Spring gerenciar as transações no BD.

#### Adequando os testes de integração com a persistência de BD usando Testcontainers

- [https://www.testcontainers.org/](https://www.testcontainers.org/)
  nota: Não feito porque usado BD em memória

#### Implementação de Segurança

- Inclusão das linhas abaixo no ``pom.xml``
  ````xml
  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
  </dependency>
  ````
- Usaremos o modo básico, conforme password gerado na iniciação
- Mas com isso nossos testes integrados começarão a quebrar, por isso, vamos:
  * Definir uma senha no ``application.properties``
  * Configurar o ``RestAssured`` (nos testes) com as instruções ``.auth().basic("user", "senha")``
    ````java
    ...
    @Test
    void quandoFindAllEntaoChecaResultado() {
        RestAssured.given()
                .auth()
                .basic("user", "1234")
                ...
    }
    ````
    
- Podemos também criar um classe de configuração do Security ao invés de usarmos o ``application.properties``
  * Para isso criamos a classe (ex. ``SecurityConfiguration``), anotamos com ``@Configuration`` e ``@EnableWebSecurity``e extendemos de ````