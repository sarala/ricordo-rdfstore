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

import java.util.HashMap;

/**
 * Created by IntelliJ IDEA.
 * User: Sarala Wimalaratne
 * Date: 18/05/12
 * Time: 14:27
 */
public class SparqlQueryTemplateParser {
    private HashMap<String,SparqlQuery> sparqlQueryHashMap = new HashMap<String, SparqlQuery>();
    private String queryTemplatePath;

    public SparqlQueryTemplateParser(String queryTemplatePath) {
        this.queryTemplatePath = queryTemplatePath;
    }

    public SparqlQuery getSparqlQuery(String query){
        SparqlQuery sparqlQuery = sparqlQueryHashMap.get(query);
        if(sparqlQuery == null){
            String queryFile = queryTemplatePath + query + ".txt";

            if(query.equals("insertStatement")){
                sparqlQuery = new SparqlInsert(queryFile);
            }else{
                sparqlQuery = new SparqlSelect(queryFile);
            }
            sparqlQueryHashMap.put(query, sparqlQuery);
        }
        return sparqlQuery;
    }
}
