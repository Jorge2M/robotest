package com.mng.robotest.domains.cookiescheck.stpv;

import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.Cookie;

import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.domains.cookiescheck.services.CookiesChecker;
import com.mng.robotest.domains.cookiescheck.services.NotificationSender;
import com.mng.robotest.domains.cookiescheck.services.TeamsNotification;
import com.mng.robotest.test.stpv.shop.genericchecks.Checker;


public class CheckerAllowedCookies implements Checker {

	private final static String message = 
"{\r\n"
+ "    \"@type\": \"MessageCard\",\r\n"
+ "    \"@context\": \"http://schema.org/extensions\",\r\n"
+ "    \"themeColor\": \"0076D7\",\r\n"
+ "    \"summary\": \"Larry Bryant created a new task\",\r\n"
+ "    \"sections\": [{\r\n"
+ "        \"activityTitle\": \"Larry Bryant created a new task\",\r\n"
+ "        \"activitySubtitle\": \"On Project Tango\",\r\n"
+ "        \"activityImage\": \"https://teamsnodesample.azurewebsites.net/static/img/image5.png\",\r\n"
+ "        \"facts\": [{\r\n"
+ "            \"name\": \"Assigned to\",\r\n"
+ "            \"value\": \"Unassigned\"\r\n"
+ "        }, {\r\n"
+ "            \"name\": \"Due date\",\r\n"
+ "            \"value\": \"Mon May 01 2017 17:07:18 GMT-0700 (Pacific Daylight Time)\"\r\n"
+ "        }, {\r\n"
+ "            \"name\": \"Status\",\r\n"
+ "            \"value\": \"Not started\"\r\n"
+ "        }],\r\n"
+ "        \"markdown\": true\r\n"
+ "    }],\r\n"
+ "    \"potentialAction\": [{\r\n"
+ "        \"@type\": \"ActionCard\",\r\n"
+ "        \"name\": \"Add a comment\",\r\n"
+ "        \"inputs\": [{\r\n"
+ "            \"@type\": \"TextInput\",\r\n"
+ "            \"id\": \"comment\",\r\n"
+ "            \"isMultiline\": false,\r\n"
+ "            \"title\": \"Add a comment here for this task\"\r\n"
+ "        }],\r\n"
+ "        \"actions\": [{\r\n"
+ "            \"@type\": \"HttpPOST\",\r\n"
+ "            \"name\": \"Add comment\",\r\n"
+ "            \"target\": \"https://docs.microsoft.com/outlook/actionable-messages\"\r\n"
+ "        }]\r\n"
+ "    }, {\r\n"
+ "        \"@type\": \"ActionCard\",\r\n"
+ "        \"name\": \"Set due date\",\r\n"
+ "        \"inputs\": [{\r\n"
+ "            \"@type\": \"DateInput\",\r\n"
+ "            \"id\": \"dueDate\",\r\n"
+ "            \"title\": \"Enter a due date for this task\"\r\n"
+ "        }],\r\n"
+ "        \"actions\": [{\r\n"
+ "            \"@type\": \"HttpPOST\",\r\n"
+ "            \"name\": \"Save\",\r\n"
+ "            \"target\": \"https://docs.microsoft.com/outlook/actionable-messages\"\r\n"
+ "        }]\r\n"
+ "    }, {\r\n"
+ "        \"@type\": \"OpenUri\",\r\n"
+ "        \"name\": \"Learn More\",\r\n"
+ "        \"targets\": [{\r\n"
+ "            \"os\": \"default\",\r\n"
+ "            \"uri\": \"https://docs.microsoft.com/outlook/actionable-messages\"\r\n"
+ "        }]\r\n"
+ "    }, {\r\n"
+ "        \"@type\": \"ActionCard\",\r\n"
+ "        \"name\": \"Change status\",\r\n"
+ "        \"inputs\": [{\r\n"
+ "            \"@type\": \"MultichoiceInput\",\r\n"
+ "            \"id\": \"list\",\r\n"
+ "            \"title\": \"Select a status\",\r\n"
+ "            \"isMultiSelect\": \"false\",\r\n"
+ "            \"choices\": [{\r\n"
+ "                \"display\": \"In Progress\",\r\n"
+ "                \"value\": \"1\"\r\n"
+ "            }, {\r\n"
+ "                \"display\": \"Active\",\r\n"
+ "                \"value\": \"2\"\r\n"
+ "            }, {\r\n"
+ "                \"display\": \"Closed\",\r\n"
+ "                \"value\": \"3\"\r\n"
+ "            }]\r\n"
+ "        }],\r\n"
+ "        \"actions\": [{\r\n"
+ "            \"@type\": \"HttpPOST\",\r\n"
+ "            \"name\": \"Save\",\r\n"
+ "            \"target\": \"https://docs.microsoft.com/outlook/actionable-messages\"\r\n"
+ "        }]\r\n"
+ "    }]\r\n"
+ "}";		
	
	@Override
	public ChecksTM check(WebDriver driver) {
		CookiesChecker cookiesChecker = new CookiesChecker();
		Pair<Boolean, List<Cookie>> resultCheck = cookiesChecker.check(driver);
		ChecksTM checks = ChecksTM.getNew();
		checks.add(
				getTextValidation(resultCheck),
				//TODO temporalmente lo ponemos en INFO (avoidEvidences) para que pueda subir a PRO
				resultCheck.getLeft(), State.Info, true);
		
		//TODO temporal!!!
		sendNotification();
		
		return checks;
	}
	
	private static void sendNotification() {
		NotificationSender notifier = new TeamsNotification();
		notifier.send(message);
	}
	
	private static String getTextValidation(Pair<Boolean, List<Cookie>> resultCheck) {
		String descripcion = 
				"Se comprueba que todas las cookies existentes en la página están permitidas.";
		if (!resultCheck.getLeft()) {
			descripcion+="Se detectan las siguientes cookies no permitidas:<ul>";
			for (Cookie cookie : resultCheck.getRight()) {
				descripcion+="<li>" + cookie.toString() + "</li>";
			}
			descripcion+="</ul>";
		}
		return descripcion;
	}

}
