package com.youngeun.myhome.controller;

import antlr.collections.List;
import com.youngeun.myhome.MyhomeApplication;
import com.youngeun.myhome.PersonData;
import com.youngeun.myhome.model.Board;
import com.youngeun.myhome.model.Contract;
import com.youngeun.myhome.model.User;
import com.youngeun.myhome.repository.BoardRepository;
import com.youngeun.myhome.repository.ContractRepository;
import com.youngeun.myhome.repository.UserListRepository;
import com.youngeun.myhome.repository.UserRepository;
import com.youngeun.myhome.service.BoardService;
import com.youngeun.myhome.service.UserService;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;

@Controller
public class FHIRController {

    @Autowired
    private ContractRepository contractRepository;
    private PersonData personData;
    private MyhomeApplication myhomeApplication;
    private ContractController contractController;

    @GetMapping("/mlRequest")
    @ResponseBody
    public String MLReqeust(HttpSession session, Model model, @RequestParam(defaultValue = "") String name) throws JSONException, IOException {

        //시스템 작동 과정
        //if(data == 'eunnie' )User
        //id = 1;
        //fhir로 넘겨준 다음 1번 adl 데이터(7개 항목)을 가져와서 return 받음.
        //return 받은 값을 다시 python server로 넘겨준 다음
        //python server에서 예측 결과 값을 돌려받아 return 해주면됨.
        Contract contract_user = contractRepository.findByClient(name);
        Contract contract_ins = contractRepository.findByInstitution(name);

        String userName = contract_user.getClient();
        String analysis = null;
        String patient = null;

        //환자: eunnie , id=1
        if (userName.equals("eunnie")){
            patient = PersonData.getPersonDataAll("1");
            ArrayList<Integer> data = PersonData.getPersonData("1");
            analysis = adl_data(data);
        }
        //환자: youngeun , id=2
        else if (userName.equals("youngeun")  ){
            patient = PersonData.getPersonDataAll("2");
            ArrayList<Integer> data = PersonData.getPersonData("2");
            analysis = adl_data(data);
        }

        //환자: my"ins0" , id=3
        else if (userName.equals("myname")  ){
            patient = PersonData.getPersonDataAll("3");
            ArrayList<Integer> data = PersonData.getPersonData("3");
            analysis = adl_data(data);
        }

        //환자: client , id=4
        else if (userName.equals("client")  ){
            patient = PersonData.getPersonDataAll("4");
            ArrayList<Integer> data = PersonData.getPersonData("4");
            analysis = adl_data(data);
        }

        //환자: student , id=5
        else if (userName.equals("student")  ){
            patient = PersonData.getPersonDataAll("5");
            ArrayList<Integer> data = PersonData.getPersonData("5");
            analysis = adl_data(data);
        }

        //환자: knu , id=6
        else if (userName.equals("knu")  ){
            patient = PersonData.getPersonDataAll("6");
            ArrayList<Integer> data = PersonData.getPersonData("6");
            analysis = adl_data(data);
        }

        //환자: good , id=7
        else if (userName.equals("good")  ){
            patient = PersonData.getPersonDataAll("7");
            ArrayList<Integer> data = PersonData.getPersonData("7");
            analysis = adl_data(data);
        }

        //환자: app , id=8
        else if (userName.equals("app")  ){
            patient = PersonData.getPersonDataAll("8");
            ArrayList<Integer> data = PersonData.getPersonData("8");
            analysis = adl_data(data);
        }

        //환자: love , id=9
        else if (userName.equals("love")  ){
            patient = PersonData.getPersonDataAll("9");
            ArrayList<Integer> data = PersonData.getPersonData("9");
            analysis = adl_data(data);
        }

        //환자: 2019 , id=10
        else if (userName.equals("2019")  ){
            patient = PersonData.getPersonDataAll("10");
            ArrayList<Integer> data = PersonData.getPersonData("10");
            analysis = adl_data(data);
        }

        //환자: pink , id=11
        else if (userName.equals("pink")  ){
            patient = PersonData.getPersonDataAll("11");
            ArrayList<Integer> data = PersonData.getPersonData("11");
            analysis = adl_data(data);
        }

        //환자: mew , id=12
        else if (userName.equals("mew")  ){
            patient = PersonData.getPersonDataAll("12");
            ArrayList<Integer> data = PersonData.getPersonData("12");
            analysis = adl_data(data);
        }

        //환자: 2020 , id=13
        else if (userName.equals("2020")  ){
            patient = PersonData.getPersonDataAll("13");
            ArrayList<Integer> data = PersonData.getPersonData("13");
            analysis = adl_data(data);
        }

        //환자: 2021 , id=14
        else if (userName.equals("2021")  ){
            patient = PersonData.getPersonDataAll("14");
            ArrayList<Integer> data = PersonData.getPersonData("14");
            analysis = adl_data(data);
        }

        //환자: proj , id=15
        else if (userName.equals("proj")  ){
            patient = PersonData.getPersonDataAll("15");
            ArrayList<Integer> data = PersonData.getPersonData("15");
            analysis = adl_data(data);
        }

        //환자: sure , id=16
        else if (userName.equals("sure")  ){
            patient = PersonData.getPersonDataAll("16");
            ArrayList<Integer> data = PersonData.getPersonData("16");
            analysis = adl_data(data);
        }

        else{
            patient = PersonData.getPersonDataAll("17");
            ArrayList<Integer> data = PersonData.getPersonData("17");
            analysis = adl_data(data);
        }

        model.addAttribute("userName",userName);//환자 이름
        System.out.println("환자이름 " + userName);
        model.addAttribute("patient",patient);//adl 데이터
        System.out.println("환자데이터 " + patient);
        model.addAttribute("analysis",analysis);//머신러닝 분석결과
        //System.out.println("분석결과 " + analysis);

        session.setAttribute("userName", userName);
        session.setAttribute("patient", patient);
        session.setAttribute("analysis", analysis);

        //System.out.println("data = " + name);
        return "contract/list";
    }

    @GetMapping("/getUserData")
    public String getUserData(@RequestParam(value = "userData", required = false) String userData) {


        System.out.println("data " + userData);
        return null;
    }



    //치매,정상 환자 결과 함수
    public static String adl_data(ArrayList<Integer> data) throws JSONException, IOException {

        JSONObject json = new JSONObject();

        json.put("adldata1",data.get(0));
        json.put("adldata2",data.get(1));
        json.put("adldata3",data.get(2));
        json.put("adldata4",data.get(3));
        json.put("adldata5",data.get(4));
        json.put("adldata6",data.get(5));
        json.put("adldata7",data.get(6));

        HttpResponse response;
        String responseBody = null;

        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        try {
            HttpPost request = new HttpPost("http://127.0.0.1:5000/user");
            StringEntity params = new StringEntity(json.toString());
            request.addHeader("content-type", "application/json");
            request.setEntity(params);
            httpClient.execute(request);

            response = httpClient.execute(request);
            responseBody = EntityUtils.toString(response.getEntity());
            System.out.println(responseBody);
            return responseBody;
        } catch (Exception ex) {
            // handle exception here
            return responseBody;
        } finally {
            httpClient.close();
        }
    }
//    @PostMapping("/patient_data")
//    public String showData(@Valid PersonData personData, BindingResult bindingResult) {
//        return "contract/patient_data";//계약 철회시 동의 버튼 사라지게 됨. id값도 사라짐.
//    }

}