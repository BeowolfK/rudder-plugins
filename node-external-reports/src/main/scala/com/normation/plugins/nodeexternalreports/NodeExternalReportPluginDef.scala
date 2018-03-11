/*
*************************************************************************************
* Copyright 2014 Normation SAS
*************************************************************************************
*
* This file is part of Rudder.
*
* Rudder is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
*
* In accordance with the terms of section 7 (7. Additional Terms.) of
* the GNU General Public License version 3, the copyright holders add
* the following Additional permissions:
* Notwithstanding to the terms of section 5 (5. Conveying Modified Source
* Versions) and 6 (6. Conveying Non-Source Forms.) of the GNU General
* Public License version 3, when you create a Related Module, this
* Related Module is not considered as a part of the work and may be
* distributed under the license agreement of your choice.
* A "Related Module" means a set of sources files including their
* documentation that, without modification of the Source Code, enables
* supplementary functions or services in addition to those offered by
* the Software.
*
* Rudder is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with Rudder.  If not, see <http://www.gnu.org/licenses/>.
*
*************************************************************************************
*/

package com.normation.plugins.nodeexternalreports

import scala.xml.NodeSeq
import com.normation.plugins._
import com.normation.plugins.nodeexternalreports.service.NodeExternalReportApi
import bootstrap.liftweb.ClassPathResource
import net.liftweb.common.Loggable
import net.liftweb.http.ClasspathTemplates
import net.liftweb.http.LiftRules
import net.liftweb.sitemap.Loc.{LocGroup, Template}
import net.liftweb.sitemap.LocPath.stringToLocPath
import com.normation.plugins.PluginStatus
import net.liftweb.sitemap.Menu

class NodeExternalReportsPluginDef(api: NodeExternalReportApi, override val status: PluginStatus) extends DefaultPluginDef with Loggable {

  val basePackage = "com.normation.plugins.nodeexternalreport"

  val configFiles = Seq(ClassPathResource("demo-config-1.properties"), ClassPathResource("demo-config-2.properties"))

  def init = {

    logger.info("Report plugin correctly loaded")
    LiftRules.statelessDispatch.append(api)

  }

  def oneTimeInit : Unit = {}

  override def updateSiteMap(menus:List[Menu]) : List[Menu] = {
    val loc =
      Menu("nodeExternalReportInfo", <span>Node External Reports</span>) / "secure" / "administration" / "nodeexternalreport" >>
        LocGroup("administrationGroup") >>
        Template(() =>
          ClasspathTemplates( "nodeExternalReport" :: Nil ) openOr
          <div>Template not found</div>)

    menus.map {
      case m@Menu(l, _* ) if(l.name == "AdministrationHome") =>
        Menu(l , m.kids.toSeq :+ loc:_* )
      case m => m
    }
  }

}
