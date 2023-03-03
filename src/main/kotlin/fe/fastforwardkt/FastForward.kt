package fe.fastforwardkt

import com.google.gson.JsonObject
import fe.fastforwardkt.ext.substringNullable
import fe.gson.extensions.array
import fe.gson.extensions.keys
import java.net.URI

enum class FastForwardRules(jsonKey: String) {
    PathBase64("path_base64") {
        override fun resolve(url: String): String? {
            return url.substringNullable(url.indexOf("aHR0c"))?.decodeBase64()
        }
    },
    PathSEncoded("path_s_encoded") {
        override fun resolve(url: String): String? {
            return url.substringNullable(url.indexOf("/s/") + 3)?.decodeUrl()
        }
    },
    PathRBase64("path_r_base64") {
        override fun resolve(url: String): String? {
            return url.substringNullable(url.indexOf("/r/") + 3)?.decodeBase64()
        }
    },
    PathDlBase64("path_dl_base64") {
        override fun resolve(url: String): String? {
            return url.substringNullable(url.indexOf("/dl/") + 4)?.decodeBase64()
        }
    },
    PathAdsHex("path_ads_hex") {
        override fun resolve(url: String): String? {
            return url.substringNullable(url.indexOf("/ads/") + 5)?.decodeHex()
        }
    },
    PathUIdBase64("path_u_id_base64") {
        override fun resolve(url: String): String? {
            val data = url.substringNullable(url.indexOf("/u/") + 3)
            return data?.substringNullable(data.indexOf("/") + 1)?.decodeBase64()
        }
    },
    QueryRaw("query_raw") {
        override fun resolve(url: String): String? {
            val uri = URI(url)
            // no fe.fastforwardkt.ext.substringNullable needed as in java, query does not start with ?
            return uri.query + uri.fragment
        }
    },
    QueryBase64("query_base64") {
        override fun resolve(url: String): String? {
            return URI(url).query?.decodeBase64()
        }
    },
    HashBase64("hash_base64") {
        override fun resolve(url: String): String? {
            return URI(url).fragment?.decodeBase64()
        }
    },
    ParamUrlGeneral("param_url_general") {
        override fun resolve(url: String): String? {
            val inputUrl = url
            var url = url.substringNullable(url.indexOf("&url=") + 5)
            if (url?.substringNullable(0, 5) == "aHR0c" || url?.substringNullable(0, 7) == "bWFnbmV") {
                url = url.split("&")[0].decodeBase64()
            } else if (url?.substringNullable(0, 13) == "http%3A%2F%2F" || url?.substringNullable(
                    0,
                    14
                ) == "https%3A%2F%2F"
            ) {
                url = url.split("&")[0].decodeUrl()
            } else {
                if (url?.substringNullable(0, 7) != "http://" && url?.substringNullable(0, 8) != "https://") {
                    url = "http://" + url
                }
                url += URI(url).fragment
            }

            // TODO: return referrer
            return url
        }
    },
    ParamUrlEncoded("param_url_encoded") {
        override fun resolve(url: String): String? {
            return URI(url).query?.querySplit()?.get("url")
        }
    },
    ParamUrlRaw("param_url_raw") {
        override fun resolve(url: String): String? {
            return url.substringNullable(url.indexOf("?url=") + 5)
        }
    },
    ParamQEncoded("param_q_encoded") {
        override fun resolve(url: String): String? {
            return URI(url).query?.querySplit()?.get("q")
        }
    },
    ParamXUrlRawHttp("param_xurl_raw_http") {
        override fun resolve(url: String): String {
            return "http" + url.substringNullable(url.indexOf("?xurl=") + 6)
        }
    },
    ParamAurlEncoded("param_aurl_encoded") {
        override fun resolve(url: String): String? {
            return URI(url).query?.querySplit()?.get("aurl")
        }
    },
    ParamCapitalUrlEncoded("param_capital_url_encoded") {
        override fun resolve(url: String): String? {
            return URI(url).query?.querySplit()?.get("URL")
        }
    },
    ParamRelBase64("param_rel_base64") {
        override fun resolve(url: String): String? {
            return url.substringNullable(url.indexOf("?rel=") + 5)?.decodeBase64()
        }
    },
    ParamLinkEncoded("param_link_encoded") {
        override fun resolve(url: String): String? {
            return url.substringNullable(url.indexOf("link=") + 5)?.decodeUrl()
        }
    },
    ParamLinkBase64("param_link_base64") {
        override fun resolve(url: String): String? {
            return URI(url).query?.querySplit()?.get("link")?.decodeBase64()
        }
    },
    ParamLinkEncodedBase64("param_link_encoded_base64") {
        override fun resolve(url: String): String? {
            return url.substringNullable(url.indexOf("?link=") + 6)?.decodeBase64()?.decodeUrl()
        }
    },
    ParamKesehatanBase64("param_kesehatan_base64") {
        override fun resolve(url: String): String? {
            return url.substringNullable(url.indexOf("?kesehatan=") + 11)?.decodeBase64()
        }
    },
    ParamWildcardBase64("param_wildcard_base64") {
        override fun resolve(url: String): String? {
            return URI(url).query?.querySplit()?.values?.firstOrNull()?.decodeBase64()
        }
    },
    ParamRBase64("param_r_base64") {
        override fun resolve(url: String): String? {
            // TODO: check referrer, safe_in
            return (url.substringNullable(url.indexOf("?r=") + 3))?.decodeUrl()?.decodeBase64()
        }
    },
    ParamKareeIBase64Pipes("param_kareeI_base64_pipes") {
        override fun resolve(url: String): String? {
            return url.substringNullable(url.indexOf("?kareeI=") + 8)?.split("||")?.get(0)?.decodeBase64()
        }
    },
    ParamCrBase64("param_cr_base64") {
        override fun resolve(url: String): String? {
            return url.substringNullable(url.indexOf("cr=") + 3)?.split("&")?.get(0)?.decodeBase64()
        }
    },
    ParamABase64("param_a_base64") {
        override fun resolve(url: String): String? {
            // TODO: check split behavior
            return url.substringNullable(url.indexOf("?a=") + 3)?.split("#")?.get(0)?.decodeBase64()
        }
    },
    ParamUrlBase64("param_url_base64") {
        override fun resolve(url: String): String? {
            return url.substringNullable(url.indexOf("?url=") + 5)?.split("&")?.get(0)?.decodeBase64()
        }
    },
    ParamIdBase64("param_id_base64") {
        override fun resolve(url: String): String? {
            return url.substringNullable(url.indexOf("?id=") + 4)?.decodeBase64()
        }
    },
    ParamGetBase64("param_get_base64") {
        override fun resolve(url: String): String? {
            val arg = url.substringNullable(url.indexOf("?get=") + 5)
            return arg?.substringNullable(0, arg.length - 1)?.decodeBase64()
        }
    },
    ParamUBase64("param_u_base64") {
        override fun resolve(url: String): String? {
            return with(URI(url)) {
                this.query?.querySplit()?.get("u") + this.fragment
            }
        }
    },
    ParamGoBase64("param_go_base64") {
        override fun resolve(url: String): String? {
            var b64 = url.substringNullable(url.indexOf("?go=") + 4)?.split("&")?.get(0)
            if (b64?.substringNullable(0, 5) == "0OoL1") {
                b64 = "aHR0c" + b64.substringNullable(5)
            }

            return b64?.decodeBase64()
        }
    },
    ParamSiteBase64("param_site_base64") {
        override fun resolve(url: String): String? {
            return url.substringNullable(url.indexOf("?site=") + 6)?.split("&")?.get(0)?.decodeBase64()
        }
    },
    ParamReffBase64("param_reff_base64") {
        override fun resolve(url: String): String? {
            return url.substringNullable(url.indexOf("?reff=") + 6)?.decodeBase64()
        }
    },
    ParamSEncoded("param_s_encoded") {
        override fun resolve(url: String): String? {
            // TODO: handle referrer
            return url.substringNullable(url.indexOf("?s=") + 3)?.decodeUrl()
        }
    },
    ParamDlEncodedBase64("param_dl_encoded_base64") {
        override fun resolve(url: String): String? {
            return url.substringNullable(url.indexOf("?dl=") + 4)?.split("&")?.get(0)?.decodeBase64()
        }
    },
    ParamHealthEncodedBase64("param_health_encoded_base64") {
        override fun resolve(url: String): String? {
            return url.substringNullable(url.indexOf("?health=") + 8)?.decodeBase64()
        }
    },
    ParamIdReverseBase64("param_id_reverse_base64") {
        override fun resolve(url: String): String? {
            val url = URI(url)
            val split = url.query?.querySplit()
            if (split?.containsKey("id") == true) {
                var t = split["id"]?.split("")?.reversed()?.joinToString()
                if (t?.substringNullable(-16) == "\" target=\"_blank") {
                    t = t.substringNullable(0, t.length - 16)
                }
                return t
            }

            return null
        }
    },
    ParamTokenBase64("param_token_base64") {
        override fun resolve(url: String): String? {
            return URI(url).query?.querySplit()?.get("token")?.decodeBase64()
        }
    },
    ParamHrefEncoded("param_href_encoded") {
        override fun resolve(url: String): String? {
            return URI(url).query?.querySplit()?.get("href")
        }
    },
    ParamShortEncoded("param_short_encoded") {
        override fun resolve(url: String): String? {
            return URI(url).query?.querySplit()?.get("short")
        }
    },
    ParamIdBase64Replacements("param_id_base64_replacements") {
        override fun resolve(url: String): String? {
            return URI(url).query?.querySplit()?.get("id")?.split("!")?.joinToString("a")
                ?.split(")")?.joinToString("e")?.split("_")?.joinToString("i")
                ?.split("(")?.joinToString("o")?.split("*")?.joinToString("u")
                ?.decodeBase64()
        }
    },
    ParamDestEncoded("param_dest_encoded") {
        override fun resolve(url: String): String? {
            return URI(url).query?.querySplit()?.get("dest")
        }
    },
    ParamGoHex("param_go_hex") {
        override fun resolve(url: String): String? {
            return URI(url).query?.querySplit()?.get("go")?.decodeHex()
        }
    },
    ParamToBase64("param_to_base64") {
        override fun resolve(url: String): String? {
            return URI(url).query?.querySplit()?.get("to")?.decodeBase64()
        }
    },
    ParamDataBase64("param_data_base64") {
        override fun resolve(url: String): String? {
            return URI(url).query?.querySplit()?.get("data")?.decodeBase64()
        }
    };
//    UseragentChrome("useragent_chrome"),
//    UseragentEmpty("useragent_empty"),
//    UseragentIphone("useragent_iphone") ,
//    RedirectPersistIdPath("redirect_persist_id_path") ,
//    RedirectPersistIdPath1Letter("redirect_persist_id_path_1_letter") ,
//    ContributeHash("contribute_hash"),
//    Tracker("tracker"),
//    TrackerForceHttp("tracker_force_http"),
//    IpLogger("ip_logger")

//    LinkvertiseSafeIn("linkvertise_safe_in");

    companion object {
        fun findByJsonKeyName(jsonKey: String) = FastForwardRules.values().find { it.name == jsonKey }
    }

    abstract fun resolve(url: String): String?
}

fun getRuleRedirect(url: String, ruleObject: JsonObject, debugPrint: Boolean = false): String? {
    ruleObject.keys().map { (key, value) -> key to value.asJsonArray }.forEach { (key, array) ->
        val rule = FastForwardRules.findByJsonKeyName(key)
        if (debugPrint) {
            println("No rule found for $key")
        }

        if (rule != null) {
            array.map { Regex(it.asJsonPrimitive.asString) }.forEach { regex ->
                if (regex.matches(url)) {
                    if (debugPrint) {
                        println("Regex $regex matches $url")
                    }
                    return rule.resolve(url)
                }
            }
        }
    }

    return null
}

fun isTracker(url: String, ruleObject: JsonObject): Boolean {
    return ruleObject.array("tracker").map { Regex(it.asJsonPrimitive.asString) }.find {
        it.matches(url)
    } != null
}
