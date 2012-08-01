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

import java.io.*;
import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: Sarala Wimalaratne
 * Date: 14/05/12
 * Time: 17:03
 */
public class SparqlSelect implements SparqlQuery {
    private ArrayList<String> selectList = new ArrayList<String>();
    private final String SPACE = " ";
    private String queryFile;
    private String ruleClause = "";
    private String fromClause = "";
    private String selectClause = "";
    private String whereClause = "";
    

    public SparqlSelect(String queryFile) {
        this.queryFile = queryFile;
        passQueryFile();
        passSelectClause();
    }

    private void passQueryFile(){
        try {
            InputStream inputStream = SparqlInsert.class.getResourceAsStream(queryFile);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String stringLine;
            while((stringLine = bufferedReader.readLine()) != null){
                if(stringLine.startsWith("define")){
                    ruleClause = stringLine;
                }
                else if(stringLine.startsWith("SELECT")){
                    selectClause = stringLine;
                }
                else if(stringLine.startsWith("FROM")){
                    if(fromClause.isEmpty())
                        fromClause = stringLine;
                    else
                        fromClause= fromClause +" " +stringLine;
                }
                else
                    whereClause = whereClause+stringLine;
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void passSelectClause(){
        String [] selectArray = selectClause.split(SPACE);
        for (String select :selectArray){
            if(select.startsWith("?")){
                selectList.add(select);
            }
        }        
    }
    
    private String constructWhereClause(String input){
        return whereClause.replace("[]",input);
    }
    
    public String getQuery(String input){
        String query = ruleClause + SPACE +
                selectClause + SPACE +
                fromClause + SPACE;
        
        if(input.isEmpty())
            query += whereClause;
        else
            query += constructWhereClause(input);

        return query;
    }

    public ArrayList<String> getSelectList() {
        return selectList;
    }
}
