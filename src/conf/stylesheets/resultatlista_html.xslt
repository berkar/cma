<?xml version="1.0" encoding="iso-8859-1"?>
<xsl:stylesheet
  xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"
  xmlns:java="http://xml.apache.org/xslt/java"
  exclude-result-prefixes="java">
<xsl:template match="/">
  <html>
    <head>
      <title>Resultatlista <xsl:value-of select="//common/name"/></title>
      <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1"/>
      <!--<link rel="stylesheet" type="text/css" href="cma.css"/>-->
      <style>
      body
      {
        font-family: Verdana, Arial, Helvetica, sans-serif;
        font-size: x-small
      }
      p
      {
        font-family: Verdana, Arial, Helvetica, sans-serif;
        font-size: x-small
      }
      h1
      {
        font-family: Verdana, Arial, Helvetica, sans-serif
      }
      h2
      {
        font-family: Verdana, Arial, Helvetica, sans-serif
      }
      h3
      {
        font-family: Verdana, Arial, Helvetica, sans-serif
      }
      h4
      {
        font-family: Verdana, Arial, Helvetica, sans-serif
      }
      h5
      {
        font-family: Verdana, Arial, Helvetica, sans-serif
      }
      h6
      {
        font-family: Verdana, Arial, Helvetica, sans-serif
      }
      tt
      {
        font-family: "Courier New", Courier, mono; font-size: x-small
      }
      li
      {
        font-family: Verdana, Arial, Helvetica, sans-serif;
        font-size: x-small
      }
      th
      {
        font-family: Verdana, Arial, Helvetica, sans-serif;
        font-size: x-small;
        font-weight: bold;
        text-align: left;
        background-color: #EEEEEE;

        border: silver solid;
              border-left: 1px white solid;
              border-top: 1px white solid;
              border-right: 1px silver solid;
              border-bottom: 1px silver solid
      }
      td
      {
        font-family: Verdana, Arial, Helvetica, sans-serif;
        font-size: x-small
      }
      caption
      {
        font-family: Verdana, Arial, Helvetica, sans-serif;
        font-size: x-small
      }
      a
      {
        text-decoration: none;
        color: #000000;
        cursor:hand
      }
      a:hover
      {
        background-color: #FFFFCC;
        cursor:hand
      }
      a:active
      {
            COLOR: #000000;
            BACKGROUND-COLOR: #ffffcc;
        cursor:hand;
      }
      a:selectedNode
      {

            COLOR: #000000;
            BACKGROUND-COLOR: #ffffcc
        cursor:hand;

      }
      </style>
    </head>
    <body>
      <h3 align="center">
        Resultatlista <xsl:value-of select="//common/name"/>
      </h3>
      <table width="100%">
      <xsl:apply-templates select="/competition/common"/>
      <xsl:apply-templates select="/competition/classes/class"/>
      </table>
    </body>
  </html>
</xsl:template>

<xsl:template match="common">
        <tr><td colspan="10"><hr size="1"/></td></tr>
        <tr>
            <td colspan="10" align="center">
                <table>
                    <tr><td><strong>Datum: <xsl:value-of select="date"/></strong></td></tr>
                </table>
            </td>
        </tr>
        <tr><td colspan="10"><hr size="1"/></td></tr>
</xsl:template>

<xsl:template match="class">
  <xsl:if test="count(registration) > 0">
  <tr>
    <td colspan="10" align="center">
      <strong>
        <xsl:value-of select="@name"/>, <xsl:value-of select="@distance"/>km
      </strong>
    </td>
  </tr>
  <tr>
    <th>Placering</th>
    <th>Startnr</th>
    <th>Namn</th>
    <th>Hund(ar)</th>
    <th>Klubb</th>
    <th>M1</th>
    <th>M2</th>
    <th>M3</th>
    <th>Totaltid</th>
    <th>Diff</th>
  </tr>
    <xsl:apply-templates select="registration[position]">
      <xsl:sort select="*[name()='position']" data-type="number" order="ascending"/>
    </xsl:apply-templates>
    <xsl:apply-templates select="registration[abandon[text()='true']]"/>
    <xsl:apply-templates select="registration[disqualified[text()='true']]"/>

      <xsl:apply-templates select="registration[starting[text()='true'] and not(totaltime) and abandon[text()='false'] and disqualified[text()='false']]">
          <xsl:with-param name="color">blue</xsl:with-param>
          <xsl:with-param name="symbol">&lt;?&gt;</xsl:with-param>
      </xsl:apply-templates>

      <xsl:apply-templates select="registration[starting[text()='true'] and not(position) and totaltime and abandon[text()='false'] and disqualified[text()='false']]">
          <xsl:with-param name="color">red</xsl:with-param>
          <xsl:with-param name="symbol">&lt;!&gt;</xsl:with-param>
      </xsl:apply-templates>

      <xsl:apply-templates select="registration[starting[text()='false']]">
          <xsl:with-param name="color">green</xsl:with-param>
      </xsl:apply-templates>

    <tr><td colspan="10"><hr size="1"/></td></tr>
    <tr><td colspan="10"><p/></td></tr>
    <tr><td colspan="10"><p/></td></tr>
  </xsl:if>
</xsl:template>

<xsl:template match="registration">
	<xsl:param name="color">black</xsl:param>
    <xsl:param name="symbol"/>
  <tr>
    <td>
        <xsl:value-of select="position"/>
        <xsl:if test="warning[text()='true']">&lt;Varning&gt;</xsl:if>
        <xsl:if test="abandon[text()='true']">&lt;Bryter&gt;</xsl:if>
        <xsl:if test="disqualified[text()='true']">&lt;Diskad&gt;</xsl:if>
        <xsl:value-of select="$symbol"/>
    </td>
    <td>
      <xsl:element name="font">
        <xsl:attribute name="color"><xsl:value-of select="$color"/></xsl:attribute>
        <xsl:value-of select="startnumber"/>
      </xsl:element>
    </td>
    <td><xsl:value-of select="name"/></td>
    <td><xsl:value-of select="dogs"/></td>
    <td><xsl:value-of select="club"/></td>

    <td>
        <xsl:if test="time1">
            <xsl:variable name="time1" select="time1"/>
            <xsl:variable name="stime1" select="java:cma.util.Time.interval2String(time1)"/>
            <xsl:text>[</xsl:text>
            <xsl:value-of select="$stime1"/>
            <xsl:text>]</xsl:text>
        </xsl:if>
    </td>
      <td>
          <xsl:if test="time2">
              <xsl:variable name="time2" select="time2"/>
              <xsl:variable name="stime2" select="java:cma.util.Time.interval2String(time2)"/>
              <xsl:text>[</xsl:text>
              <xsl:value-of select="$stime2"/>
              <xsl:text>]</xsl:text>
          </xsl:if>
      </td>
      <td>
          <xsl:if test="time3">
              <xsl:variable name="time3" select="time3"/>
              <xsl:variable name="stime3" select="java:cma.util.Time.interval2String(time3)"/>
              <xsl:text>[</xsl:text>
              <xsl:value-of select="$stime3"/>
              <xsl:text>]</xsl:text>
          </xsl:if>
      </td>
    <td>
      <xsl:if test="totaltime">
        <xsl:variable name="totaltime" select="totaltime"/>
        <xsl:variable name="stotaltime" select="java:cma.util.Time.interval2String($totaltime)"/>
          <xsl:text>[</xsl:text>
        <xsl:value-of select="$stotaltime"/>
          <xsl:text>]</xsl:text>
      </xsl:if>
    </td>
    <td>
      <xsl:if test="difftime">
        <xsl:variable name="difftime" select="difftime"/>
        <xsl:variable name="sdifftime" select="java:cma.util.Time.interval2String($difftime)"/>
          <xsl:text>[</xsl:text>
        <xsl:value-of select="$sdifftime"/>
          <xsl:text>]</xsl:text>
      </xsl:if>
    </td>
  </tr>
</xsl:template>

</xsl:stylesheet>
