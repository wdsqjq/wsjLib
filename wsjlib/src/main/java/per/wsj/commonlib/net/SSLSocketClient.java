package per.wsj.commonlib.net;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

/**
 * SSL证书
 * Created by shiju.wang on 2018/1/11.
 */

public class SSLSocketClient {

    /**
     * 获取这个SSLSocketFactory 忽略证书
     *
     * @return
     */
    public static SSLSocketFactory getSSLSocketFactoryIgnore() {
        try {
            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, getTrustManager(), new SecureRandom());
            return sslContext.getSocketFactory();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 根据证书生成SSLSocketFactory
     *
     * @return
     */
    public static SSLSocketFactory getSSLSocketFactory(Context context, String assetName) {
        SSLSocketFactory sslSocketFactory;
        try {
//            TrustManager[] trustManager = getTrustManager(context, assetName);
            X509TrustManager x509TrustManager = getX509TrustManager(context, assetName);

            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null,
                    new TrustManager[]{x509TrustManager},
                    new SecureRandom());
            sslSocketFactory = sslContext.getSocketFactory();
            return sslSocketFactory;
        } catch (Exception e) {
            e.printStackTrace();
            sslSocketFactory = getSSLSocketFactoryIgnore();
        }
        return sslSocketFactory;
    }

    /**
     * 信任所有证书的X509TrustManager
     *
     * @return
     */
    public static X509TrustManager getX509TrustManager() {
        X509TrustManager x509TrustManager = (X509TrustManager) getTrustManager()[0];
        return x509TrustManager;
    }

    /**
     * 根据证书生成的X509TrustManager
     *
     * @param context
     * @param assetName
     * @return
     */
    public static X509TrustManager getX509TrustManager(Context context, String assetName) {
        X509TrustManager x509TrustManager;
        try {
            TrustManager[] trustManager = getTrustManager(context, assetName);
            x509TrustManager = new MyTrustManager(chooseTrustManager(trustManager));
            return x509TrustManager;
        } catch (Exception e) {
            x509TrustManager = (X509TrustManager) getTrustManager()[0];
        }
        return x509TrustManager;
    }

    /**
     * 获取TrustManager 信任所有证书
     *
     * @return
     */
    private static TrustManager[] getTrustManager() {
        TrustManager[] trustManagers = new TrustManager[]{new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

            }

            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[]{};
            }
        }
        };
        return trustManagers;
    }

    /**
     * 获取TrustManager 根据证书生成
     *
     * @param assetName
     * @return
     */
    private static TrustManager[] getTrustManager(Context context, String assetName) throws CertificateException, KeyStoreException, IOException, NoSuchAlgorithmException {

        CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
        KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
        keyStore.load(null);
        InputStream is = context.getAssets().open(assetName);
        String certificateAlias = Integer.toString(0);
        keyStore.setCertificateEntry(certificateAlias, certificateFactory.generateCertificate(is));//拷贝好的证书
        is.close();
//            InputStream[] certificates = {is};
//            int index = 0;
//            for (InputStream certificate : certificates) {
//                String certificateAlias = Integer.toString(index++);
//                keyStore.setCertificateEntry(certificateAlias, certificateFactory.generateCertificate(certificate));
//                certificate.close();
//            }
        TrustManagerFactory trustManagerFactory =
                TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(keyStore);
        return trustManagerFactory.getTrustManagers();
    }

    //获取HostnameVerifier
    public static HostnameVerifier getHostnameVerifier() {
        HostnameVerifier hostnameVerifier = new HostnameVerifier() {
            @Override
            public boolean verify(String s, SSLSession sslSession) {
                return true;
            }
        };
        return hostnameVerifier;
    }

    private static X509TrustManager chooseTrustManager(TrustManager[] trustManagers) {
        for (TrustManager trustManager : trustManagers) {
            if (trustManager instanceof X509TrustManager) {
                return (X509TrustManager) trustManager;
            }
        }
        return null;
    }

    private static class MyTrustManager implements X509TrustManager {
        private X509TrustManager defaultTrustManager;
        private X509TrustManager localTrustManager;

        public MyTrustManager(X509TrustManager localTrustManager) throws NoSuchAlgorithmException, KeyStoreException {
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init((KeyStore) null);
            defaultTrustManager = chooseTrustManager(trustManagerFactory.getTrustManagers());
            this.localTrustManager = localTrustManager;
        }

        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            try {
                defaultTrustManager.checkServerTrusted(chain, authType);
            } catch (CertificateException ce) {
                localTrustManager.checkServerTrusted(chain, authType);
            }
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    }

    public static class SSLParams {
        public SSLSocketFactory sslSocketFactory;
        public X509TrustManager x509TrustManager;

        public SSLParams() {
        }
    }
}
