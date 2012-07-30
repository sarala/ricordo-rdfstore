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

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: Sarala Wimalaratne
 * Date: 27/07/12
 * Time: 16:36
 */
public class SparqlInsert implements SparqlQuery{

    private String insertQueryfile;
    private String insertClause;
    private String valueClause;
    private final String SEPERATOR = ",";
    private final String SPACE = " ";

    public SparqlInsert(String insertQueryfile) {
        this.insertQueryfile = insertQueryfile;
        passInsertQueryFile();
    }

    private void passInsertQueryFile(){
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(insertQueryfile));
            String stringLine;
            while((stringLine = bufferedReader.readLine()) != null){
                if(stringLine.startsWith("INSERT")){
                    insertClause = stringLine;
                }
                else
                    valueClause = stringLine;
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getQuery(String input){
        String [] values = input.split(SEPERATOR);
        String [] valueClauseValues = valueClause.split(SPACE);

        if(values.length!=3 || valueClauseValues.length!=5)
            return null;

        String query = insertClause + SPACE;
        query += valueClauseValues[0] + SPACE;
        query += constructValueClause(valueClauseValues[1],values[0])+ SPACE;
        query += constructValueClause(valueClauseValues[2],values[1])+ SPACE;
        query += constructValueClause(valueClauseValues[3],values[2])+ SPACE;
        query += valueClauseValues[4] + SPACE;

        return query;
    }

    public String constructValueClause(String target, String input){
        return target.replace("[]",input);
    }
}
