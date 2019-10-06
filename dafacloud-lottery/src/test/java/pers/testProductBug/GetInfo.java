package pers.testProductBug;

import pers.utils.dafaRequest.DafaRequest;

public class GetInfo {

    public static void main(String[] args) {
        String url ="https://m.5cai001.com/v1/users/info";

        String result = DafaRequest.get(url,"91836744E3998BE15F6F22A18DE6383D");

        System.out.println(result);



    }


}
