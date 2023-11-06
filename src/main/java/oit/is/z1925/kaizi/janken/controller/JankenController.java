package oit.is.z1925.kaizi.janken.controller;

import java.security.Principal;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import oit.is.z1925.kaizi.janken.model.Entry;
import oit.is.z1925.kaizi.janken.model.UserMapper;
import oit.is.z1925.kaizi.janken.model.User;
import oit.is.z1925.kaizi.janken.model.Match;
import oit.is.z1925.kaizi.janken.model.MatchMapper;

@Controller
public class JankenController {

  @PostMapping("/janken")
  public String janken_post(@RequestParam String username, ModelMap model) {
    model.addAttribute("username", username);
    return "janken.html";
  }

  @Autowired
  private Entry room;
  @Autowired
  UserMapper userMapper;
  @Autowired
  MatchMapper matchMapper;

  @GetMapping("/janken")
  public String janken(Principal prin, ModelMap model) {
    String loginUser = prin.getName();
    ArrayList<User> Users = userMapper.selectAllUsers();
    ArrayList<Match> Matches = matchMapper.selectAllMatches();
    // User user = userMapper.selectById(1);
    this.room.addUser(loginUser);
    model.addAttribute("room", this.room);
    model.addAttribute("loginUser", loginUser);
    model.addAttribute("Users", Users);
    model.addAttribute("Matches", Matches);

    return "janken.html";
  }

  @GetMapping("/janken/{my_hand}")
  public String janken(@PathVariable Integer my_hand, ModelMap model) {
    int enemy_hand = 1;
    String judge = "", mhand = "", ehand = "";

    if (my_hand == 1 & enemy_hand == 2 || my_hand == 2 & enemy_hand == 3 || my_hand == 3 & enemy_hand == 1) {
      judge = "勝利";
    } else if (my_hand == 1 & enemy_hand == 3 || my_hand == 2 & enemy_hand == 1 || my_hand == 3 & enemy_hand == 2) {
      judge = "負け";
    } else {
      judge = "あいこ";
    }

    switch (my_hand) {
      case 1:
        mhand = "グー";
        break;
      case 2:
        mhand = "チョキ";
        break;
      case 3:
        mhand = "パー";
        break;
      default:
        break;
    }
    switch (enemy_hand) {
      case 1:
        ehand = "グー";
        break;
      case 2:
        ehand = "チョキ";
        break;
      case 3:
        ehand = "パー";
        break;
      default:
        break;
    }
    model.addAttribute("mhand", mhand);
    model.addAttribute("ehand", ehand);
    model.addAttribute("judge", judge);

    return "janken.html";

  }

}
