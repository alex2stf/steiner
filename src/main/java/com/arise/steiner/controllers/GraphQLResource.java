package com.arise.steiner.controllers;

import static graphql.schema.idl.TypeRuntimeWiring.newTypeWiring;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.Resources;
import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import java.io.IOException;
import java.io.StringWriter;
import java.net.URL;
import org.apache.commons.io.Charsets;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/graphql")
@SuppressWarnings("unused")
public class GraphQLResource {


    private final GraphQLDataFetchers graphQLDataFetchers;
    private GraphQL graphQL;
    private String sdl;

    public GraphQLResource(GraphQLDataFetchers graphQLDataFetchers) throws IOException {
        this.graphQLDataFetchers = graphQLDataFetchers;
        URL url = Resources.getResource("schema.graphqls");

        sdl = Resources.toString(url, Charsets.UTF_8);
        GraphQLSchema graphQLSchema = buildSchema(sdl);
        this.graphQL = GraphQL.newGraphQL(graphQLSchema).build();
    }

    private GraphQLSchema buildSchema(String sdl) {
        TypeDefinitionRegistry typeRegistry = new SchemaParser().parse(sdl);
        RuntimeWiring runtimeWiring = buildWiring();
        SchemaGenerator schemaGenerator = new SchemaGenerator();
        return schemaGenerator.makeExecutableSchema(typeRegistry, runtimeWiring);
    }


    private RuntimeWiring buildWiring() {
        return RuntimeWiring.newRuntimeWiring()

            .type(newTypeWiring("Query")
                .dataFetcher("filesByTags", graphQLDataFetchers.getFileByTagDataFetcher()))

            .type(newTypeWiring("Query")
                .dataFetcher("documentsByProductIdAndStatus", graphQLDataFetchers.getDocumentsByProdutIdAndStatus()))
            .type(newTypeWiring("Query")
                .dataFetcher("documentsByProductIdAndStatusAndPhase", graphQLDataFetchers.documentsByProductIdAndStatusAndPhase()))


            .build();
    }

    @RequestMapping(value = "/view", method = RequestMethod.GET, produces = "application/graphql")
    public ResponseEntity viewSchema() {
        return new ResponseEntity(sdl, HttpStatus.OK);
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity search(@RequestBody String query) throws IOException {
        ExecutionResult executionResult = graphQL.execute(query);

        ObjectMapper objectMapper = new ObjectMapper();
        StringWriter stringWriter = new StringWriter();

        if (executionResult.getErrors() != null && !executionResult.getErrors().isEmpty()) {
            objectMapper.writeValue(stringWriter, executionResult.getErrors());
        } else {
            objectMapper.writeValue(stringWriter, executionResult.getData());
        }

        return new ResponseEntity(stringWriter.toString(), HttpStatus.OK);
    }
}
