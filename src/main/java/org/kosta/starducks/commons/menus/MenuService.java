package org.kosta.starducks.commons.menus;

import jakarta.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.List;
////

/**
 * 서브메뉴 조회
 */
public class MenuService {


    // 이름       코드      하위메뉴
    // 마이페이지  mypage    attendance, schedule, conf
    // 전자결재   document   mydocu, createDoc, recieved, tempsaved
    // 전사게시판  forum
    // 인사부     hr        emp(사원관리), attend, vacation, dept, docu
    // 물류유통부 logistic   store(입고), release(출고), inventory(재고)
    // 총무부     general   adschedule, adforum, adconf, adproduct
    // 재무부     fina      point(지점), vendor(거래처)


    /**
     * 상위 메뉴에 따른 하위 메뉴 조회
     *
     * @param code 상위 메뉴 코드
     * @return
     */
    public static List<MenuDetail> gets(String code) {
//        System.out.println("code" + code);
        List<MenuDetail> menus = new ArrayList<>();

        // 게시판 하위 메뉴
        switch (code) {
            case "mypage":
                menus.add(new MenuDetail("attendance", "Attendance", "/mypage/attendance"));
                menus.add(new MenuDetail("schedule", "Schedule", "/mypage/schedule/show"));
                menus.add(new MenuDetail("conf", "Room Booking", "/mypage/conf"));
                break;
            case "document":
                menus.add(new MenuDetail("createDoc", "Create Document", "/document/createDoc"));
                menus.add(new MenuDetail("submitDoc", "Approval Request", "/document/submitDoc"));
                menus.add(new MenuDetail("receiveDoc", "Approval Inbox", "/document/receiveDoc"));
                menus.add(new MenuDetail("tempList", "Drafts", "/document/tempList"));
                break;
            case "hr":
                menus.add(new MenuDetail("emp", "Staff", "/hr/emp"));
                menus.add(new MenuDetail("attend", "Staff-Attendance", "/hr/attend"));
                menus.add(new MenuDetail("vacation", "Staff-Leave", "/"));
                menus.add(new MenuDetail("dept", "Department", "/hr/dept"));
                menus.add(new MenuDetail("docu", "Document", "/"));
                break;
            case "logistic":
                menus.add(new MenuDetail("inbound", "Inbound", "/logistic/inbound/warehouse/list"));
                menus.add(new MenuDetail("outbound", "Outbound", "/logistic/outbound/warehouse/list"));
                menus.add(new MenuDetail("stock", "Inventory", "/logistic/stock/warehouse/list"));
                break;
            case "general":
                menus.add(new MenuDetail("schedule", "Schedule", "/general/schedule"));
                menus.add(new MenuDetail("adforum", "E-Board Management", "/"));
                menus.add(new MenuDetail("adconf", "Room Management", "/"));
                menus.add(new MenuDetail("products", "Items", "/general/products/list"));
                break;
            case "fina" :
                menus.add(new MenuDetail("store", "Branch Info", "/fina/store/list"));
                menus.add(new MenuDetail("vendor", "Vendor Info", "/fina/vendor/list"));
                break;
            default:
                menus.add(new MenuDetail("attendance", "Attendance", "/mypage/attendance"));
                menus.add(new MenuDetail("schedule", "Schedule", "/mypage/schedule"));
                menus.add(new MenuDetail("confroom", "Room Booking", "/mypage/conf"));
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
//        System.out.println("uri: " + URI);
        return URI.substring(URI.indexOf("/") + 1);
    }
}