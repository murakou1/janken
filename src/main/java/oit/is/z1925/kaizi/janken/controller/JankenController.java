package oit.is.z1925.kaizi.janken.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * jankenController
 *
 * クラスの前に@Controllerをつけていると，HTTPリクエスト（GET/POSTなど）があったときに，このクラスが呼び出される
 */
@Controller
public class JankenController {

  @GetMapping("/janken2")
  public String janken2() {
    return "janken.html";
  }

  /**
   * POSTを受け付ける場合は@PostMappingを利用する /janken1へのPOSTを受け付けて，FormParamで指定された変数(input
   * name)をjanken1()メソッドの引数として受け取ることができる
   *
   * @param name
   * @param model
   * @return
   */
  @PostMapping("/janken1")
  public String janken1(@RequestParam String username, ModelMap model) {
    model.addAttribute("username", username);
    return "janken.html";
  }

  /**
   * @param my_hand
   * @param model
   * @return
   */

  @GetMapping("/janken2/{my_hand}")
  public String janken2(@PathVariable Integer my_hand, ModelMap model) {
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
