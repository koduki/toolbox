/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apps;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 *
 * @author koduki
 */
public class TextSearch {

    public static void main(String[] args) {
        var text = "Seleniumのようなテストの自動実行を行うサービス## 概要### Zero InstallationCreating a test is as simple as entering a URL and using your site or application. No installation whatsoever.### Write Once, Run AnytimeReflect tests are authored and run within our cloud-based browser, which produces a human-readable test plan that you can run as often as you'd like.### Mobile TestingLooking for mobile support? Reflect supports mobile and tablet testing out-of-the-box, and can even test React native apps.### Framework AgnosticReflect supports a wide range of user interactions that work across all Javascript frameworks.## 参考- [Seleniumの代替！毎月30分の無料枠がついた自動Webテスト「Reflect」](https://itnews.org/news_contents/reflect)";
        var seachWords = "テスト自動化 Web";

        var textTokens = parseNgram(text, 2);
        System.out.println(textTokens);

        var searchTokens = parseNgram(seachWords, 2);
        System.out.println(searchTokens);
        var matches = searchTokens.stream().filter(x -> textTokens.contains(x))
                .collect(Collectors.toList());
        System.out.println(matches);

        System.out.println(matches.size() / ((float) searchTokens.size()));
    }

    static ArrayList<String> parseNgram(String text, int n) {
        var xs = new ArrayList<String>();
        for (int i = 0; i < text.length() - (n - 1); i++) {
            xs.add(text.substring(i, i + n));
        }
        return xs;
    }

}
