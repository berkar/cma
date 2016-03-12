<?xml version="1.0" encoding="iso-8859-1"?>
<xsl:stylesheet
  xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"
  xmlns:java="http://xml.apache.org/xslt/java"
  exclude-result-prefixes="java">
<xsl:template match="/">
  <html>
    <head>
      <title>ESDRA Resultlist<xsl:value-of select="//common/name"/></title>
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
        ESDRA Resultlist <xsl:value-of select="//common/name"/>
      </h3>
      <table width="100%">
      <xsl:apply-templates select="/competition/common"/>
      <xsl:apply-templates select="/competition/classes/class"/>
      </table>
    </body>
  </html>
</xsl:template>

<xsl:template match="common">
        <tr><td colspan="5"><hr size="1"/></td></tr>
        <tr>
            <td colspan="5" align="center">
                <table>
                    <tr><td><strong>Date: <xsl:value-of select="date"/></strong></td></tr>
                </table>
            </td>
        </tr>
        <tr><td colspan="5"><hr size="1"/></td></tr>
</xsl:template>

<xsl:template match="class">
  <xsl:if test="count(registration) > 0">
  <tr>
    <td colspan="5" align="center">
      <strong>
        <xsl:value-of select="@name"/>, <xsl:value-of select="@distance"/>km
      </strong>
    </td>
  </tr>
  <tr>
    <th width="5%">Position</th>
    <th width="10%">DID</th>
    <th>Name</th>
    <th>Totaltime</th>
    <th></th>
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

    <tr><td colspan="5"><hr size="1"/></td></tr>
    <tr><td colspan="5"><p/></td></tr>
    <tr><td colspan="5"><p/></td></tr>
  </xsl:if>
</xsl:template>

<xsl:template match="registration">
	<xsl:param name="color">black</xsl:param>
    <xsl:param name="symbol"/>
  <tr>
    <td>
        <xsl:value-of select="position"/>
        <xsl:if test="warning[text()='true']">&lt;Warning&gt;</xsl:if>
        <xsl:if test="abandon[text()='true']">&lt;Abandon&gt;</xsl:if>
        <xsl:if test="disqualified[text()='true']">&lt;Disqualified&gt;</xsl:if>
        <xsl:value-of select="$symbol"/>
    </td>
    <td>
      <xsl:element name="font">
        <xsl:value-of select="did"/>
      </xsl:element>
    </td>
    <td>
      <xsl:value-of select="name"/>
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
    <td></td>
  </tr>
</xsl:template>

</xsl:stylesheet>
