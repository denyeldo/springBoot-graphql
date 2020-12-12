# springBoot-graphql
springboot project with jpa and graphql integration

git clone <repo>
  
maven buld

run the main class file: GraphqlServiceApplication.java

The schema exposed at : http://localhost:8080/graphql/schema.json

Use a graphql client like Altair Graph QL client: Url: http://localhost:8080/graphql/

Body example:

query {
    findAllBooks{
      title,
      author{
        firstName,
        lastName
      }
    }
}

Please refer to schema files in resource files.
