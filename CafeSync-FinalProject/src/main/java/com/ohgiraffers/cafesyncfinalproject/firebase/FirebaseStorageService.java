package com.ohgiraffers.cafesyncfinalproject.firebase;

import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
public class FirebaseStorageService {

    private static final String FIREBASE_STORAGE_BASE_URL = "https://firebasestorage.googleapis.com/v0/b/cafe-sync.firebasestorage.app/o/";

    /**
     * ✅ `gs://` 경로를 HTTP URL로 변환하는 함수
     */
    public String convertGsUrlToHttp(String gsUrl) {
        if (gsUrl == null || !gsUrl.startsWith("gs://")) {
            return gsUrl; // 잘못된 값이면 그대로 반환
        }

        // gs://cafe-sync.appspot.com/menu/iceAmericano.jpg → menu/iceAmericano.jpg 변환
        String path = gsUrl.replace("gs://cafe-sync.firebasestorage.app/", "");
        String encodedPath = URLEncoder.encode(path, StandardCharsets.UTF_8);

        return FIREBASE_STORAGE_BASE_URL + encodedPath + "?alt=media";
    }
}
