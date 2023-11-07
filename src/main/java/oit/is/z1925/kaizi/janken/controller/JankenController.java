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
import oit.is.z1925.kaizi.janken.model.MatchInfo;
import oit.is.z1925.kaizi.janken.model.MatchInfoMapper;
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
  @Autowired
  MatchInfoMapper matchInfoMapper;

  @GetMapping("/janken")
  public String janken(Principal prin, ModelMap model) {
    String loginUser = prin.getName();
    ArrayList<User> Users = userMapper.selectAllUsers();
    ArrayList<Match> Matches = matchMapper.selectAllMatches();
    ArrayList<MatchInfo> Info = matchInfoMapper.selectAllMatchInfo();
    // User user = userMapper.selectById(1);
    this.room.addUser(loginUser);
    model.addAttribute("room", this.room);
    model.addAttribute("loginUser", loginUser);
    model.addAttribute("Users", Users);
    model.addAttribute("Matches", Matches);
    model.addAttribute("Info", Info);

    return "janken.html";
  }

  @GetMapping("/fight/{my_hand}")
  @Transactional
  public String fight(@PathVariable Integer my_hand, @RequestParam Integer id, Principal prin, ModelMap model) {
    int enemy_hand = 1;
    String judge = "", mhand = "", ehand = "";
    String loginUser = prin.getName();
    User enemy = userMapper.selectById(id);
    MatchInfo matchInfo = new MatchInfo();
    int my_id = userMapper.selectByName(loginUser);
    boolean isActive = true;

    if (my_hand == 1 & enemy_hand == 2 || my_hand == 2 & enemy_hand == 3 || my_hand == 3 & enemy_hand == 1) {
      judge = "勝利";
    } else if (my_hand == 1 & enemy_hand == 3 || my_hand == 2 & enemy_hand == 1 || my_hand == 3 & enemy_hand == 2) {
      judge = "負け";
    } else {
      judge = "あいこ";
    }

    switch (my_hand) {
      case 1:
        mhand = "Gu";
        break;
      case 2:
        mhand = "Choki";
        break;
      case 3:
        mhand = "Pa";
        break;
      default:
        break;
    }
    switch (enemy_hand) {
      case 1:
        ehand = "Gu";
        break;
      case 2:
        ehand = "Choki";
        break;
      case 3:
        ehand = "Pa";
        break;
      default:
        break;
    }

    matchInfo.setUser1(my_id);
    matchInfo.setUser2(id);
    matchInfo.setUser1Hand(mhand);
    // match.setUser2Hand(ehand);
    matchInfo.setIsActive(isActive);
    matchInfoMapper.insertMatchInfo(matchInfo);
    model.addAttribute("loginUser", loginUser);
    model.addAttribute("enemy", enemy);
    model.addAttribute("mhand", mhand);
    model.addAttribute("ehand", ehand);
    model.addAttribute("judge", judge);

    return "wait.html";

  }

  @GetMapping("/match")
  public String match(@RequestParam Integer id, Principal prin, ModelMap model) {
    String loginUser = prin.getName();
    User enemy = userMapper.selectById(id);
    model.addAttribute("enemy", enemy);
    model.addAttribute("loginUser", loginUser);

    return "match.html";
  }

  /*
   * @GetMapping("/fight/{my_hand}")
   *
   * @Transactional
   * public String fight(@PathVariable Integer my_hand, @RequestParam Integer id,
   * Principal prin, ModelMap model) {
   * int enemy_hand = 1;
   * String judge = "", mhand = "", ehand = "";
   * String loginUser = prin.getName();
   * User enemy = userMapper.selectById(id);
   * Match match = new Match();
   * int my_id = userMapper.selectByName(loginUser);
   * boolean isActive = true;
   *
   * if (my_hand == 1 & enemy_hand == 2 || my_hand == 2 & enemy_hand == 3 ||
   * my_hand == 3 & enemy_hand == 1) {
   * judge = "勝利";
   * } else if (my_hand == 1 & enemy_hand == 3 || my_hand == 2 & enemy_hand == 1
   * || my_hand == 3 & enemy_hand == 2) {
   * judge = "負け";
   * } else {
   * judge = "あいこ";
   * }
   *
   * switch (my_hand) {
   * case 1:
   * mhand = "Gu";
   * break;
   * case 2:
   * mhand = "Choki";
   * break;
   * case 3:
   * mhand = "Pa";
   * break;
   * default:
   * break;
   * }
   * switch (enemy_hand) {
   * case 1:
   * ehand = "Gu";
   * break;
   * case 2:
   * ehand = "Choki";
   * break;
   * case 3:
   * ehand = "Pa";
   * break;
   * default:
   * break;
   * }
   *
   * match.setUser1(my_id);
   * match.setUser2(id);
   * match.setUser1Hand(mhand);
   * match.setUser2Hand(ehand);
   * match.setIsActive(isActive);
   * matchMapper.insertMatch(match);
   * model.addAttribute("loginUser", loginUser);
   * model.addAttribute("enemy", enemy);
   * model.addAttribute("mhand", mhand);
   * model.addAttribute("ehand", ehand);
   * model.addAttribute("judge", judge);
   *
   * return "match.html";
   *
   * }
   */

}
