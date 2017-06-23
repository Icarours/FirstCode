package com.syl.weatherforecast.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Bright on 2017/6/23.
 *
 * @Describe
 * @Called
 */

public class HeWeather implements Serializable {

    /**
     * aqi : {"city":{"aqi":"60","co":"1","no2":"42","o3":"43","pm10":"69","pm25":"30","qlty":"良","so2":"6"}}
     * basic : {"city":"苏州","cnty":"中国","id":"CN101190401","lat":"31.29937935","lon":"120.61958313","update":{"loc":"2017-06-23 09:50","utc":"2017-06-23 01:50"}}
     * daily_forecast : [{"astro":{"mr":"04:00","ms":"18:05","sr":"04:55","ss":"19:06"},"cond":{"code_d":"300","code_n":"310","txt_d":"阵雨","txt_n":"暴雨"},"date":"2017-06-23","hum":"83","pcpn":"7.5","pop":"100","pres":"1003","tmp":{"max":"27","min":"24"},"uv":"5","vis":"13","wind":{"deg":"159","dir":"东南风","sc":"3-4","spd":"12"}},{"astro":{"mr":"04:57","ms":"19:10","sr":"04:55","ss":"19:06"},"cond":{"code_d":"307","code_n":"300","txt_d":"大雨","txt_n":"阵雨"},"date":"2017-06-24","hum":"85","pcpn":"0.3","pop":"100","pres":"1000","tmp":{"max":"26","min":"24"},"uv":"9","vis":"15","wind":{"deg":"145","dir":"西风","sc":"4-5","spd":"24"}},{"astro":{"mr":"06:00","ms":"20:10","sr":"04:56","ss":"19:06"},"cond":{"code_d":"302","code_n":"300","txt_d":"雷阵雨","txt_n":"阵雨"},"date":"2017-06-25","hum":"82","pcpn":"3.2","pop":"97","pres":"1002","tmp":{"max":"26","min":"23"},"uv":"7","vis":"13","wind":{"deg":"186","dir":"西风","sc":"3-4","spd":"15"}}]
     * hourly_forecast : [{"cond":{"code":"104","txt":"阴"},"date":"2017-06-23 10:00","hum":"77","pop":"58","pres":"1006","tmp":"27","wind":{"deg":"125","dir":"东南风","sc":"微风","spd":"6"}},{"cond":{"code":"309","txt":"毛毛雨/细雨"},"date":"2017-06-23 13:00","hum":"75","pop":"95","pres":"1005","tmp":"27","wind":{"deg":"161","dir":"东南风","sc":"微风","spd":"7"}},{"cond":{"code":"306","txt":"中雨"},"date":"2017-06-23 16:00","hum":"79","pop":"95","pres":"1003","tmp":"27","wind":{"deg":"181","dir":"南风","sc":"微风","spd":"14"}},{"cond":{"code":"305","txt":"小雨"},"date":"2017-06-23 19:00","hum":"83","pop":"98","pres":"1002","tmp":"26","wind":{"deg":"205","dir":"西南风","sc":"微风","spd":"13"}},{"cond":{"code":"305","txt":"小雨"},"date":"2017-06-23 22:00","hum":"85","pop":"48","pres":"1002","tmp":"26","wind":{"deg":"212","dir":"西南风","sc":"微风","spd":"11"}}]
     * now : {"cond":{"code":"101","txt":"多云"},"fl":"24","hum":"84","pcpn":"0","pres":"1003","tmp":"25","vis":"8","wind":{"deg":"116","dir":"南风","sc":"微风","spd":"8"}}
     * status : ok
     * suggestion : {"comf":{"brf":"较舒适","txt":"白天有雨，从而使空气湿度加大，会使人们感觉有点儿闷热，但早晚的天气很凉爽、舒适。"},"cw":{"brf":"不宜","txt":"不宜洗车，未来24小时内有雨，如果在此期间洗车，雨水和路上的泥水可能会再次弄脏您的爱车。"},"drsg":{"brf":"舒适","txt":"建议着长袖T恤、衬衫加单裤等服装。年老体弱者宜着针织长袖衬衫、马甲和长裤。"},"flu":{"brf":"较易发","txt":"风较大，阴冷潮湿，较易发生感冒，体质较弱的朋友请注意适当防护。"},"sport":{"brf":"较不宜","txt":"有降水，且风力较强，气压较低，推荐您在室内进行低强度运动；若坚持户外运动，须注意避雨防风。"},"trav":{"brf":"适宜","txt":"有降水，温度适宜，在细雨中游玩别有一番情调，可不要错过机会呦！但记得出门要携带雨具。"},"uv":{"brf":"弱","txt":"紫外线强度较弱，建议出门前涂擦SPF在12-15之间、PA+的防晒护肤品。"}}
     */

    private AqiBean aqi;
    private BasicBean basic;
    private NowBean now;
    private String status;
    private SuggestionBean suggestion;
    private List<DailyForecastBean> daily_forecast;
    private List<HourlyForecastBean> hourly_forecast;

    public AqiBean getAqi() {
        return aqi;
    }

    public void setAqi(AqiBean aqi) {
        this.aqi = aqi;
    }

    public BasicBean getBasic() {
        return basic;
    }

    public void setBasic(BasicBean basic) {
        this.basic = basic;
    }

    public NowBean getNow() {
        return now;
    }

    public void setNow(NowBean now) {
        this.now = now;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public SuggestionBean getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(SuggestionBean suggestion) {
        this.suggestion = suggestion;
    }

    public List<DailyForecastBean> getDaily_forecast() {
        return daily_forecast;
    }

    public void setDaily_forecast(List<DailyForecastBean> daily_forecast) {
        this.daily_forecast = daily_forecast;
    }

    public List<HourlyForecastBean> getHourly_forecast() {
        return hourly_forecast;
    }

    public void setHourly_forecast(List<HourlyForecastBean> hourly_forecast) {
        this.hourly_forecast = hourly_forecast;
    }

    @Override
    public String toString() {
        return "HeWeather{" +
                "aqi=" + aqi +
                ", basic=" + basic +
                ", now=" + now +
                ", status='" + status + '\'' +
                ", suggestion=" + suggestion +
                ", daily_forecast=" + daily_forecast +
                ", hourly_forecast=" + hourly_forecast +
                '}';
    }

    public static class AqiBean implements Serializable {
        @Override
        public String toString() {
            return "AqiBean{" +
                    "city=" + city +
                    '}';
        }

        /**
         * city : {"aqi":"60","co":"1","no2":"42","o3":"43","pm10":"69","pm25":"30","qlty":"良","so2":"6"}
         */

        private CityBean city;

        public CityBean getCity() {
            return city;
        }

        public void setCity(CityBean city) {
            this.city = city;
        }

        public static class CityBean implements Serializable {
            /**
             * aqi : 60
             * co : 1
             * no2 : 42
             * o3 : 43
             * pm10 : 69
             * pm25 : 30
             * qlty : 良
             * so2 : 6
             */

            private String aqi;
            private String co;
            private String no2;
            private String o3;
            private String pm10;
            private String pm25;
            private String qlty;
            private String so2;

            @Override
            public String toString() {
                return "CityBean{" +
                        "aqi='" + aqi + '\'' +
                        ", co='" + co + '\'' +
                        ", no2='" + no2 + '\'' +
                        ", o3='" + o3 + '\'' +
                        ", pm10='" + pm10 + '\'' +
                        ", pm25='" + pm25 + '\'' +
                        ", qlty='" + qlty + '\'' +
                        ", so2='" + so2 + '\'' +
                        '}';
            }

            public String getAqi() {
                return aqi;
            }

            public void setAqi(String aqi) {
                this.aqi = aqi;
            }

            public String getCo() {
                return co;
            }

            public void setCo(String co) {
                this.co = co;
            }

            public String getNo2() {
                return no2;
            }

            public void setNo2(String no2) {
                this.no2 = no2;
            }

            public String getO3() {
                return o3;
            }

            public void setO3(String o3) {
                this.o3 = o3;
            }

            public String getPm10() {
                return pm10;
            }

            public void setPm10(String pm10) {
                this.pm10 = pm10;
            }

            public String getPm25() {
                return pm25;
            }

            public void setPm25(String pm25) {
                this.pm25 = pm25;
            }

            public String getQlty() {
                return qlty;
            }

            public void setQlty(String qlty) {
                this.qlty = qlty;
            }

            public String getSo2() {
                return so2;
            }

            public void setSo2(String so2) {
                this.so2 = so2;
            }
        }
    }

    public static class BasicBean implements Serializable {
        @Override
        public String toString() {
            return "BasicBean{" +
                    "city='" + city + '\'' +
                    ", cnty='" + cnty + '\'' +
                    ", id='" + id + '\'' +
                    ", lat='" + lat + '\'' +
                    ", lon='" + lon + '\'' +
                    ", update=" + update +
                    '}';
        }

        /**
         * city : 苏州
         * cnty : 中国
         * id : CN101190401
         * lat : 31.29937935
         * lon : 120.61958313
         * update : {"loc":"2017-06-23 09:50","utc":"2017-06-23 01:50"}
         */

        private String city;
        private String cnty;
        private String id;
        private String lat;
        private String lon;
        private UpdateBean update;

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getCnty() {
            return cnty;
        }

        public void setCnty(String cnty) {
            this.cnty = cnty;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public String getLon() {
            return lon;
        }

        public void setLon(String lon) {
            this.lon = lon;
        }

        public UpdateBean getUpdate() {
            return update;
        }

        public void setUpdate(UpdateBean update) {
            this.update = update;
        }

        public static class UpdateBean implements Serializable {
            @Override
            public String toString() {
                return "UpdateBean{" +
                        "loc='" + loc + '\'' +
                        ", utc='" + utc + '\'' +
                        '}';
            }

            /**
             * loc : 2017-06-23 09:50
             * utc : 2017-06-23 01:50
             */

            private String loc;
            private String utc;

            public String getLoc() {
                return loc;
            }

            public void setLoc(String loc) {
                this.loc = loc;
            }

            public String getUtc() {
                return utc;
            }

            public void setUtc(String utc) {
                this.utc = utc;
            }
        }
    }

    public static class NowBean implements Serializable {
        @Override
        public String toString() {
            return "NowBean{" +
                    "cond=" + cond +
                    ", fl='" + fl + '\'' +
                    ", hum='" + hum + '\'' +
                    ", pcpn='" + pcpn + '\'' +
                    ", pres='" + pres + '\'' +
                    ", tmp='" + tmp + '\'' +
                    ", vis='" + vis + '\'' +
                    ", wind=" + wind +
                    '}';
        }

        /**
         * cond : {"code":"101","txt":"多云"}
         * fl : 24
         * hum : 84
         * pcpn : 0
         * pres : 1003
         * tmp : 25
         * vis : 8
         * wind : {"deg":"116","dir":"南风","sc":"微风","spd":"8"}
         */

        private CondBean cond;
        private String fl;
        private String hum;
        private String pcpn;
        private String pres;
        private String tmp;
        private String vis;
        private WindBean wind;

        public CondBean getCond() {
            return cond;
        }

        public void setCond(CondBean cond) {
            this.cond = cond;
        }

        public String getFl() {
            return fl;
        }

        public void setFl(String fl) {
            this.fl = fl;
        }

        public String getHum() {
            return hum;
        }

        public void setHum(String hum) {
            this.hum = hum;
        }

        public String getPcpn() {
            return pcpn;
        }

        public void setPcpn(String pcpn) {
            this.pcpn = pcpn;
        }

        public String getPres() {
            return pres;
        }

        public void setPres(String pres) {
            this.pres = pres;
        }

        public String getTmp() {
            return tmp;
        }

        public void setTmp(String tmp) {
            this.tmp = tmp;
        }

        public String getVis() {
            return vis;
        }

        public void setVis(String vis) {
            this.vis = vis;
        }

        public WindBean getWind() {
            return wind;
        }

        public void setWind(WindBean wind) {
            this.wind = wind;
        }

        public static class CondBean implements Serializable {
            @Override
            public String toString() {
                return "CondBean{" +
                        "code='" + code + '\'' +
                        ", txt='" + txt + '\'' +
                        '}';
            }

            /**
             * code : 101
             * txt : 多云
             */

            private String code;
            private String txt;

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getTxt() {
                return txt;
            }

            public void setTxt(String txt) {
                this.txt = txt;
            }
        }

        public static class WindBean implements Serializable {
            @Override
            public String toString() {
                return "WindBean{" +
                        "deg='" + deg + '\'' +
                        ", dir='" + dir + '\'' +
                        ", sc='" + sc + '\'' +
                        ", spd='" + spd + '\'' +
                        '}';
            }

            /**
             * deg : 116
             * dir : 南风
             * sc : 微风
             * spd : 8
             */

            private String deg;
            private String dir;
            private String sc;
            private String spd;

            public String getDeg() {
                return deg;
            }

            public void setDeg(String deg) {
                this.deg = deg;
            }

            public String getDir() {
                return dir;
            }

            public void setDir(String dir) {
                this.dir = dir;
            }

            public String getSc() {
                return sc;
            }

            public void setSc(String sc) {
                this.sc = sc;
            }

            public String getSpd() {
                return spd;
            }

            public void setSpd(String spd) {
                this.spd = spd;
            }
        }
    }

    public static class SuggestionBean implements Serializable {
        @Override
        public String toString() {
            return "SuggestionBean{" +
                    "comf=" + comf +
                    ", cw=" + cw +
                    ", drsg=" + drsg +
                    ", flu=" + flu +
                    ", sport=" + sport +
                    ", trav=" + trav +
                    ", uv=" + uv +
                    '}';
        }

        /**
         * comf : {"brf":"较舒适","txt":"白天有雨，从而使空气湿度加大，会使人们感觉有点儿闷热，但早晚的天气很凉爽、舒适。"}
         * cw : {"brf":"不宜","txt":"不宜洗车，未来24小时内有雨，如果在此期间洗车，雨水和路上的泥水可能会再次弄脏您的爱车。"}
         * drsg : {"brf":"舒适","txt":"建议着长袖T恤、衬衫加单裤等服装。年老体弱者宜着针织长袖衬衫、马甲和长裤。"}
         * flu : {"brf":"较易发","txt":"风较大，阴冷潮湿，较易发生感冒，体质较弱的朋友请注意适当防护。"}
         * sport : {"brf":"较不宜","txt":"有降水，且风力较强，气压较低，推荐您在室内进行低强度运动；若坚持户外运动，须注意避雨防风。"}
         * trav : {"brf":"适宜","txt":"有降水，温度适宜，在细雨中游玩别有一番情调，可不要错过机会呦！但记得出门要携带雨具。"}
         * uv : {"brf":"弱","txt":"紫外线强度较弱，建议出门前涂擦SPF在12-15之间、PA+的防晒护肤品。"}
         */

        private ComfBean comf;
        private CwBean cw;
        private DrsgBean drsg;
        private FluBean flu;
        private SportBean sport;
        private TravBean trav;
        private UvBean uv;

        public ComfBean getComf() {
            return comf;
        }

        public void setComf(ComfBean comf) {
            this.comf = comf;
        }

        public CwBean getCw() {
            return cw;
        }

        public void setCw(CwBean cw) {
            this.cw = cw;
        }

        public DrsgBean getDrsg() {
            return drsg;
        }

        public void setDrsg(DrsgBean drsg) {
            this.drsg = drsg;
        }

        public FluBean getFlu() {
            return flu;
        }

        public void setFlu(FluBean flu) {
            this.flu = flu;
        }

        public SportBean getSport() {
            return sport;
        }

        public void setSport(SportBean sport) {
            this.sport = sport;
        }

        public TravBean getTrav() {
            return trav;
        }

        public void setTrav(TravBean trav) {
            this.trav = trav;
        }

        public UvBean getUv() {
            return uv;
        }

        public void setUv(UvBean uv) {
            this.uv = uv;
        }

        public static class ComfBean implements Serializable {
            @Override
            public String toString() {
                return "ComfBean{" +
                        "brf='" + brf + '\'' +
                        ", txt='" + txt + '\'' +
                        '}';
            }

            /**
             * brf : 较舒适
             * txt : 白天有雨，从而使空气湿度加大，会使人们感觉有点儿闷热，但早晚的天气很凉爽、舒适。
             */

            private String brf;
            private String txt;

            public String getBrf() {
                return brf;
            }

            public void setBrf(String brf) {
                this.brf = brf;
            }

            public String getTxt() {
                return txt;
            }

            public void setTxt(String txt) {
                this.txt = txt;
            }
        }

        public static class CwBean implements Serializable {
            @Override
            public String toString() {
                return "CwBean{" +
                        "brf='" + brf + '\'' +
                        ", txt='" + txt + '\'' +
                        '}';
            }

            /**
             * brf : 不宜
             * txt : 不宜洗车，未来24小时内有雨，如果在此期间洗车，雨水和路上的泥水可能会再次弄脏您的爱车。
             */

            private String brf;
            private String txt;

            public String getBrf() {
                return brf;
            }

            public void setBrf(String brf) {
                this.brf = brf;
            }

            public String getTxt() {
                return txt;
            }

            public void setTxt(String txt) {
                this.txt = txt;
            }
        }

        public static class DrsgBean implements Serializable {
            @Override
            public String toString() {
                return "DrsgBean{" +
                        "brf='" + brf + '\'' +
                        ", txt='" + txt + '\'' +
                        '}';
            }

            /**
             * brf : 舒适
             * txt : 建议着长袖T恤、衬衫加单裤等服装。年老体弱者宜着针织长袖衬衫、马甲和长裤。
             */

            private String brf;
            private String txt;

            public String getBrf() {
                return brf;
            }

            public void setBrf(String brf) {
                this.brf = brf;
            }

            public String getTxt() {
                return txt;
            }

            public void setTxt(String txt) {
                this.txt = txt;
            }
        }

        public static class FluBean implements Serializable {
            @Override
            public String toString() {
                return "FluBean{" +
                        "brf='" + brf + '\'' +
                        ", txt='" + txt + '\'' +
                        '}';
            }

            /**
             * brf : 较易发
             * txt : 风较大，阴冷潮湿，较易发生感冒，体质较弱的朋友请注意适当防护。
             */

            private String brf;
            private String txt;

            public String getBrf() {
                return brf;
            }

            public void setBrf(String brf) {
                this.brf = brf;
            }

            public String getTxt() {
                return txt;
            }

            public void setTxt(String txt) {
                this.txt = txt;
            }
        }

        public static class SportBean implements Serializable {
            @Override
            public String toString() {
                return "SportBean{" +
                        "brf='" + brf + '\'' +
                        ", txt='" + txt + '\'' +
                        '}';
            }

            /**
             * brf : 较不宜
             * txt : 有降水，且风力较强，气压较低，推荐您在室内进行低强度运动；若坚持户外运动，须注意避雨防风。
             */

            private String brf;
            private String txt;

            public String getBrf() {
                return brf;
            }

            public void setBrf(String brf) {
                this.brf = brf;
            }

            public String getTxt() {
                return txt;
            }

            public void setTxt(String txt) {
                this.txt = txt;
            }
        }

        public static class TravBean implements Serializable {
            @Override
            public String toString() {
                return "TravBean{" +
                        "brf='" + brf + '\'' +
                        ", txt='" + txt + '\'' +
                        '}';
            }

            /**
             * brf : 适宜
             * txt : 有降水，温度适宜，在细雨中游玩别有一番情调，可不要错过机会呦！但记得出门要携带雨具。
             */

            private String brf;
            private String txt;

            public String getBrf() {
                return brf;
            }

            public void setBrf(String brf) {
                this.brf = brf;
            }

            public String getTxt() {
                return txt;
            }

            public void setTxt(String txt) {
                this.txt = txt;
            }
        }

        public static class UvBean implements Serializable {
            @Override
            public String toString() {
                return "UvBean{" +
                        "brf='" + brf + '\'' +
                        ", txt='" + txt + '\'' +
                        '}';
            }

            /**
             * brf : 弱
             * txt : 紫外线强度较弱，建议出门前涂擦SPF在12-15之间、PA+的防晒护肤品。
             */

            private String brf;
            private String txt;

            public String getBrf() {
                return brf;
            }

            public void setBrf(String brf) {
                this.brf = brf;
            }

            public String getTxt() {
                return txt;
            }

            public void setTxt(String txt) {
                this.txt = txt;
            }
        }
    }

    public static class DailyForecastBean implements Serializable {
        @Override
        public String toString() {
            return "DailyForecastBean{" +
                    "astro=" + astro +
                    ", cond=" + cond +
                    ", date='" + date + '\'' +
                    ", hum='" + hum + '\'' +
                    ", pcpn='" + pcpn + '\'' +
                    ", pop='" + pop + '\'' +
                    ", pres='" + pres + '\'' +
                    ", tmp=" + tmp +
                    ", uv='" + uv + '\'' +
                    ", vis='" + vis + '\'' +
                    ", wind=" + wind +
                    '}';
        }

        /**
         * astro : {"mr":"04:00","ms":"18:05","sr":"04:55","ss":"19:06"}
         * cond : {"code_d":"300","code_n":"310","txt_d":"阵雨","txt_n":"暴雨"}
         * date : 2017-06-23
         * hum : 83
         * pcpn : 7.5
         * pop : 100
         * pres : 1003
         * tmp : {"max":"27","min":"24"}
         * uv : 5
         * vis : 13
         * wind : {"deg":"159","dir":"东南风","sc":"3-4","spd":"12"}
         */

        private AstroBean astro;
        private CondBeanX cond;
        private String date;
        private String hum;
        private String pcpn;
        private String pop;
        private String pres;
        private TmpBean tmp;
        private String uv;
        private String vis;
        private WindBeanX wind;

        public AstroBean getAstro() {
            return astro;
        }

        public void setAstro(AstroBean astro) {
            this.astro = astro;
        }

        public CondBeanX getCond() {
            return cond;
        }

        public void setCond(CondBeanX cond) {
            this.cond = cond;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getHum() {
            return hum;
        }

        public void setHum(String hum) {
            this.hum = hum;
        }

        public String getPcpn() {
            return pcpn;
        }

        public void setPcpn(String pcpn) {
            this.pcpn = pcpn;
        }

        public String getPop() {
            return pop;
        }

        public void setPop(String pop) {
            this.pop = pop;
        }

        public String getPres() {
            return pres;
        }

        public void setPres(String pres) {
            this.pres = pres;
        }

        public TmpBean getTmp() {
            return tmp;
        }

        public void setTmp(TmpBean tmp) {
            this.tmp = tmp;
        }

        public String getUv() {
            return uv;
        }

        public void setUv(String uv) {
            this.uv = uv;
        }

        public String getVis() {
            return vis;
        }

        public void setVis(String vis) {
            this.vis = vis;
        }

        public WindBeanX getWind() {
            return wind;
        }

        public void setWind(WindBeanX wind) {
            this.wind = wind;
        }

        public static class AstroBean implements Serializable {
            @Override
            public String toString() {
                return "AstroBean{" +
                        "mr='" + mr + '\'' +
                        ", ms='" + ms + '\'' +
                        ", sr='" + sr + '\'' +
                        ", ss='" + ss + '\'' +
                        '}';
            }

            /**
             * mr : 04:00
             * ms : 18:05
             * sr : 04:55
             * ss : 19:06
             */

            private String mr;
            private String ms;
            private String sr;
            private String ss;

            public String getMr() {
                return mr;
            }

            public void setMr(String mr) {
                this.mr = mr;
            }

            public String getMs() {
                return ms;
            }

            public void setMs(String ms) {
                this.ms = ms;
            }

            public String getSr() {
                return sr;
            }

            public void setSr(String sr) {
                this.sr = sr;
            }

            public String getSs() {
                return ss;
            }

            public void setSs(String ss) {
                this.ss = ss;
            }
        }

        public static class CondBeanX implements Serializable {
            @Override
            public String toString() {
                return "CondBeanX{" +
                        "code_d='" + code_d + '\'' +
                        ", code_n='" + code_n + '\'' +
                        ", txt_d='" + txt_d + '\'' +
                        ", txt_n='" + txt_n + '\'' +
                        '}';
            }

            /**
             * code_d : 300
             * code_n : 310
             * txt_d : 阵雨
             * txt_n : 暴雨
             */

            private String code_d;
            private String code_n;
            private String txt_d;
            private String txt_n;

            public String getCode_d() {
                return code_d;
            }

            public void setCode_d(String code_d) {
                this.code_d = code_d;
            }

            public String getCode_n() {
                return code_n;
            }

            public void setCode_n(String code_n) {
                this.code_n = code_n;
            }

            public String getTxt_d() {
                return txt_d;
            }

            public void setTxt_d(String txt_d) {
                this.txt_d = txt_d;
            }

            public String getTxt_n() {
                return txt_n;
            }

            public void setTxt_n(String txt_n) {
                this.txt_n = txt_n;
            }
        }

        public static class TmpBean implements Serializable {
            @Override
            public String toString() {
                return "TmpBean{" +
                        "max='" + max + '\'' +
                        ", min='" + min + '\'' +
                        '}';
            }

            /**
             * max : 27
             * min : 24
             */

            private String max;
            private String min;

            public String getMax() {
                return max;
            }

            public void setMax(String max) {
                this.max = max;
            }

            public String getMin() {
                return min;
            }

            public void setMin(String min) {
                this.min = min;
            }
        }

        public static class WindBeanX implements Serializable {
            @Override
            public String toString() {
                return "WindBeanX{" +
                        "deg='" + deg + '\'' +
                        ", dir='" + dir + '\'' +
                        ", sc='" + sc + '\'' +
                        ", spd='" + spd + '\'' +
                        '}';
            }

            /**
             * deg : 159
             * dir : 东南风
             * sc : 3-4
             * spd : 12
             */

            private String deg;
            private String dir;
            private String sc;
            private String spd;

            public String getDeg() {
                return deg;
            }

            public void setDeg(String deg) {
                this.deg = deg;
            }

            public String getDir() {
                return dir;
            }

            public void setDir(String dir) {
                this.dir = dir;
            }

            public String getSc() {
                return sc;
            }

            public void setSc(String sc) {
                this.sc = sc;
            }

            public String getSpd() {
                return spd;
            }

            public void setSpd(String spd) {
                this.spd = spd;
            }
        }
    }

    public static class HourlyForecastBean implements Serializable {
        @Override
        public String toString() {
            return "HourlyForecastBean{" +
                    "cond=" + cond +
                    ", date='" + date + '\'' +
                    ", hum='" + hum + '\'' +
                    ", pop='" + pop + '\'' +
                    ", pres='" + pres + '\'' +
                    ", tmp='" + tmp + '\'' +
                    ", wind=" + wind +
                    '}';
        }

        /**
         * cond : {"code":"104","txt":"阴"}
         * date : 2017-06-23 10:00
         * hum : 77
         * pop : 58
         * pres : 1006
         * tmp : 27
         * wind : {"deg":"125","dir":"东南风","sc":"微风","spd":"6"}
         */

        private CondBeanXX cond;
        private String date;
        private String hum;
        private String pop;
        private String pres;
        private String tmp;
        private WindBeanXX wind;

        public CondBeanXX getCond() {
            return cond;
        }

        public void setCond(CondBeanXX cond) {
            this.cond = cond;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getHum() {
            return hum;
        }

        public void setHum(String hum) {
            this.hum = hum;
        }

        public String getPop() {
            return pop;
        }

        public void setPop(String pop) {
            this.pop = pop;
        }

        public String getPres() {
            return pres;
        }

        public void setPres(String pres) {
            this.pres = pres;
        }

        public String getTmp() {
            return tmp;
        }

        public void setTmp(String tmp) {
            this.tmp = tmp;
        }

        public WindBeanXX getWind() {
            return wind;
        }

        public void setWind(WindBeanXX wind) {
            this.wind = wind;
        }

        public static class CondBeanXX implements Serializable {
            @Override
            public String toString() {
                return "CondBeanXX{" +
                        "code='" + code + '\'' +
                        ", txt='" + txt + '\'' +
                        '}';
            }

            /**
             * code : 104
             * txt : 阴
             */

            private String code;
            private String txt;

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getTxt() {
                return txt;
            }

            public void setTxt(String txt) {
                this.txt = txt;
            }
        }

        public static class WindBeanXX implements Serializable {
            @Override
            public String toString() {
                return "WindBeanXX{" +
                        "deg='" + deg + '\'' +
                        ", dir='" + dir + '\'' +
                        ", sc='" + sc + '\'' +
                        ", spd='" + spd + '\'' +
                        '}';
            }

            /**
             * deg : 125
             * dir : 东南风
             * sc : 微风
             * spd : 6
             */

            private String deg;
            private String dir;
            private String sc;
            private String spd;

            public String getDeg() {
                return deg;
            }

            public void setDeg(String deg) {
                this.deg = deg;
            }

            public String getDir() {
                return dir;
            }

            public void setDir(String dir) {
                this.dir = dir;
            }

            public String getSc() {
                return sc;
            }

            public void setSc(String sc) {
                this.sc = sc;
            }

            public String getSpd() {
                return spd;
            }

            public void setSpd(String spd) {
                this.spd = spd;
            }
        }
    }
}
