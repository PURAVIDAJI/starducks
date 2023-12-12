package org.kosta.starducks.commons;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * 서브메뉴 조회
 */
public class MenuService {


    // 이름       코드      하위메뉴
    // 마이페이지  mypage    attend, schedule, confroom
    // 전자결재   document   mydocu, writedocu, recieved, tempsaved
    // 전사게시판  forum
    // 인사부     hr        employee(사원관리), attend, vacation, dept, docu
    // 물류유통부 logistic   store(입고), release(출고), inventory(재고)
    // 총무부     general   adschedule, adforum, adconf, adproduct
    // 재무부     fina      point(지점), vendor(거래처)



    /**
     * 상위 메뉴에 따른 하위 메뉴 조회
     * @param code 상위 메뉴 코드
     * @return
     */
    public static List<MenuDetail> gets(String code) {
        System.out.println("code" + code);
        List<MenuDetail> menus = new ArrayList<>();

        // 게시판 하위 메뉴
        switch (code) {
            case "mypage":
                menus.add(new MenuDetail("attend", "근태관리", "/"));
                menus.add(new MenuDetail("schedule", "일정관리", "/schedule"));
                menus.add(new MenuDetail("confroom", "회의실 예약", "/"));
                break;
            case "document":
                menus.add(new MenuDetail("mydocu", "나의 결재", "/"));
                menus.add(new MenuDetail("writedocu", "결재문서 작성", "/"));
                menus.add(new MenuDetail("recieved", "수신함", "/"));
                menus.add(new MenuDetail("tempsaved", "임시저장함", "/"));
                break;
            case "hr" :
                menus.add(new MenuDetail("employee", "사원 관리", "/hr"));
                menus.add(new MenuDetail("attend", "근태 관리", "/"));
                menus.add(new MenuDetail("vacation", "휴가 관리", "/"));
                menus.add(new MenuDetail("dept", "부서 관리", "/"));
                menus.add(new MenuDetail("docu", "문서 관리", "/"));
                break;
            case "logistic" :
                menus.add(new MenuDetail("store", "입고 관리", "/"));
                menus.add(new MenuDetail("release", "출고 관리", "/"));
                menus.add(new MenuDetail("inventory", "재고 관리", "/"));
                break;
            case "general" :
                menus.add(new MenuDetail("adschedule", "전사 일정 관리", "/"));
                menus.add(new MenuDetail("adforum", "게시판 관리", "/"));
                menus.add(new MenuDetail("adconf", "회의실 관리", "/"));
                menus.add(new MenuDetail("adproduct", "품목 관리", "/"));
                break;
            case "fina" :
                menus.add(new MenuDetail("point", "지점 정보 관리", "/"));
                menus.add(new MenuDetail("vendor", "거래처 정보 관리", "/"));
                break;
            default:
                menus.add(new MenuDetail("attend", "근태관리", "/"));
                menus.add(new MenuDetail("schedule", "일정관리", "/"));
                menus.add(new MenuDetail("confroom", "회의실 예약", "/"));
        }
//        if (code.equals("mypage")) {
//        } else if (code.equals("document")) {
//        } else if (code.equals("hr")) {
//        } else if (code.equals("logistic")) {
//        } else if (code.equals("general")) {
//        } else if (code.equals("fina")) {
//        }

        return menus;
    }

    public static String getSubMenuCode(HttpServletRequest request) {
        String URI = request.getRequestURI();
        System.out.println("uri: " + URI);
        return URI.substring(URI.indexOf("/")+1);
    }

    public static void commonProcess(HttpServletRequest request, Model model, String code) {
        String URI = request.getRequestURI();

        // 메뉴
        model.addAttribute("menuCode", code);

        // 서브 메뉴 처리
        String subMenuCode = getSubMenuCode(request);
        model.addAttribute("subMenuCode", subMenuCode);
        model.addAttribute("URI", URI);

        List<MenuDetail> submenus = gets(code);
        if(submenus.size() > 0) {
            model.addAttribute("submenus", submenus);
        }

    }
}
