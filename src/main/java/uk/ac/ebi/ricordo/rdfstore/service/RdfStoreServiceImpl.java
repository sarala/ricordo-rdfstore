/*
 * Copyright 2012 EMBL-EBI
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.ac.ebi.ricordo.rdfstore.service;

import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import uk.ac.ebi.ricordo.rdfstore.bean.Resource;
import uk.ac.ebi.ricordo.rdfstore.bean.ResourceList;
import virtuoso.jena.driver.*;

import java.util.ArrayList;


/**
 * Created by IntelliJ IDEA.
 * User: Sarala Wimalaratne
 * Date: 02/03/12
 * Time: 14:22
 */
public class RdfStoreServiceImpl implements RdfStoreService {

    private VirtGraph virtGraph;
    private SparqlQueryTemplateParser queryTemplateParser;

    public RdfStoreServiceImpl(VirtGraph virtGraph, SparqlQueryTemplateParser queryTemplateParser){
        this.virtGraph = virtGraph;
        this.queryTemplateParser = queryTemplateParser;
    }

     /**
     * Queries for all models
      * @param query search input
      * @param command
      * @param resourceList
     */
    public void search(String query, String command, ResourceList resourceList) {
        SparqlSelect sparqlSelect = (SparqlSelect)queryTemplateParser.getSparqlQuery(command);
        resourceList.setSelect(sparqlSelect.getSelectList());

        ArrayList<Resource> idList = new ArrayList<Resource>();
        ResultSet resultSet = executeQuery(sparqlSelect.getQuery(query));
        while(resultSet.hasNext()){
            Resource resource = new Resource();
            QuerySolution solution = resultSet.next();
            ArrayList<String> valueList = new ArrayList<String>();
            for(String select : sparqlSelect.getSelectList()) {
                if (solution.getResource(select) != null) {
                    valueList.add(solution.getResource(select).getURI());
                    resource.setValue(valueList);
                }
            }
            idList.add(resource);
        }

        resourceList.setResources(idList);
    }

    public void insert(String query, String command){
        SparqlQuery sparqlQuery = queryTemplateParser.getSparqlQuery(command);
        insertStatement(sparqlQuery.getQuery(query));
    }


    private ResultSet executeQuery(String query){
        VirtuosoQueryExecution vqe = VirtuosoQueryExecutionFactory.create(query, virtGraph);
        return vqe.execSelect();    
    }

    private void insertStatement(String query){
        VirtuosoUpdateRequest vur = VirtuosoUpdateFactory.create(query, virtGraph);
        vur.exec();
    }


}
