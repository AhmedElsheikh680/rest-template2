package com.spring.controller;

import com.spring.model.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.xml.ws.Response;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("team-players")
public class TeamController {

    @Autowired
    private RestTemplate restTemplate;

    private static String BASE_PLAYER_URL="http://localhost:4444/players";

    @GetMapping("/team/{firstName}/player/{lastName}")
    public ResponseEntity<Team> getPlayerByFirstNameLastName(@PathVariable String firstName, @PathVariable String lastName){
//        Map<String, String> urlAttr = new HashMap<>();
//        urlAttr.put("firstName", firstName);
//        urlAttr.put("lastName", lastName);
//
//        ResponseEntity<Team> teamResponseEntity = restTemplate.getForEntity(
//                BASE_PLAYER_URL+ "/{firstName}/player/{lastName}",
//                    Team.class,
//                urlAttr
//        );
//        Team team =  teamResponseEntity.getBody();
////        return ResponseEntity.ok(team);
//        return new ResponseEntity<>(new Team(team.getId(), team.getFirstName(), team.getLastName(), team.getEmail()), HttpStatus.OK);



        Map<String, String> urlAttr = new HashMap<>();
        urlAttr.put("firstName", firstName);
        urlAttr.put("lastName", lastName);
        ResponseEntity<Team> teamResponseEntity = new RestTemplate().getForEntity(
                "http://localhost:4444/players/{firstName}/player/{lastName}",
                Team.class,
                urlAttr

      );
        Team teamResponse  = teamResponseEntity.getBody();
        return new ResponseEntity<>(
                new Team(teamResponse.getId(), teamResponse.getFirstName(),
                        teamResponse.getLastName(), teamResponse.getEmail()), HttpStatus.OK);
    }

    @GetMapping("/team/{id}")
    public ResponseEntity<Team>  getById(@PathVariable Long id){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<Team> httpEntity = new HttpEntity<>(httpHeaders);
        ResponseEntity<Team> teamResponseEntity = restTemplate.exchange(BASE_PLAYER_URL+ "/"+id, HttpMethod.GET, httpEntity, Team.class);

            return ResponseEntity.ok(teamResponseEntity.getBody());
    }

    @GetMapping("/teams")
    public ResponseEntity<?> getAllTeams(){
//        ResponseEntity<List> listResponseEntity = restTemplate.getForEntity(BASE_PLAYER_URL, List.class);
//
//        return ResponseEntity.ok(listResponseEntity.getBody());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<Team> httpEntity = new HttpEntity<>(httpHeaders);
        ResponseEntity<List> teamResponseEntity =  restTemplate.exchange(BASE_PLAYER_URL, HttpMethod.GET, httpEntity, List.class);
        return ResponseEntity.ok(teamResponseEntity.getBody());
    }


    @PostMapping("/team")
    public ResponseEntity<?> addTeam(@RequestBody Team team){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("accept", "application/json");
        httpHeaders.add("accept-language", "en");
        HttpEntity<Team> httpEntity = new HttpEntity<>(team, httpHeaders);
        ResponseEntity<Team> teamResponseEntity = restTemplate.postForEntity(BASE_PLAYER_URL+"/player", httpEntity, Team.class);
        return ResponseEntity.ok(teamResponseEntity.getBody());
    }

    @PutMapping("/team")
    public ResponseEntity<?> updateTeam(@RequestBody Team team){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<Team> httpEntity = new HttpEntity<>(team, httpHeaders);
        ResponseEntity<Team> responseEntity = restTemplate.exchange(BASE_PLAYER_URL+"/player", HttpMethod.PUT, httpEntity, Team.class);
        return ResponseEntity.ok(responseEntity.getBody());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePlayer(@PathVariable Long id){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<Team> httpEntity= new HttpEntity<>(httpHeaders);
        ResponseEntity<Team> responseEntity = restTemplate.exchange(BASE_PLAYER_URL+"/"+id, HttpMethod.DELETE, httpEntity, Team.class);
        return ResponseEntity.ok(responseEntity.getBody());
    }
}
