<?xml version="1.0" encoding="iso-8859-1"?>
<xsl:stylesheet
  xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"
  xmlns:java="http://xml.apache.org/xslt/java"
  exclude-result-prefixes="java">
<xsl:template match="/">
  <html>
    <head>
      <title>Startlista <xsl:value-of select="//common/name"/>, <xsl:value-of select="//common/date"/></title>
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
      <h3 align="center">Startlista <xsl:value-of select="//common/name"/>, <xsl:value-of select="//common/date"/></h3>
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
      <table width="80%">
        <tr>
          <th>Datum</th>
          <th>Start tävlingsklocka</th>
          <th>Start intervall</th>
          <th>Klass intervall</th>
          <th>Första startnumret</th>
        </tr>
        <tr>
          <td><xsl:value-of select="date"/></td>
          <td>
              <xsl:variable name="time" select="time"/>
              <xsl:variable name="stime" select="java:cma.util.Time.interval2String($time)"/>
              <xsl:value-of select="$stime"/>
          </td>
          <td><xsl:value-of select="startinterval"/> sek.</td>
          <td><xsl:value-of select="classinterval"/> sek.</td>
          <td><xsl:value-of select="firstnumber"/></td>
        </tr>
      </table>
    </td>
  </tr>
  <tr><td colspan="5"><hr size="1"/></td></tr>
</xsl:template>

<xsl:template match="class">
  <xsl:if test="count(registration[startnumber]) > 0">
    <tr>
      <td colspan="5" align="center">
        <strong>
            <xsl:value-of select="@name"/>, <xsl:value-of select="@distance"/>km
        </strong>
      </td>
    </tr>
    <tr>
      <th>Startnr</th>
      <th>Starttid</th>
      <th>Namn</th>
      <th>Hundar</th>
      <th>Klubb</th>
    </tr>
    <xsl:apply-templates select="registration[startnumber]">
      <xsl:sort select="*[name()='startnumber']" data-type="number" order="ascending"/>
    </xsl:apply-templates>

    <tr><td colspan="5"><hr size="1"/></td></tr>
    <tr><td colspan="5"><p/></td></tr>
    <tr><td colspan="5"><p/></td></tr>
  </xsl:if>
</xsl:template>

<xsl:template match="registration">
  <tr>
    <td>
        <xsl:value-of select="startnumber"/>
        <xsl:if test="starting[text()='true']">
            <xsl:text> (ok)</xsl:text>
        </xsl:if>
        <xsl:if test="starting[text()='false']">
            <xsl:text> (-)</xsl:text>
        </xsl:if>
    </td>
    <td>
        <xsl:variable name="starttime" select="starttime"/>
        <xsl:variable name="sstarttime" select="java:cma.util.Time.interval2String($starttime)"/>
        <xsl:value-of select="$sstarttime"/>
    </td>
    <td>
        <xsl:if test="payment[text()='true']">
          <xsl:element name="font">
            <xsl:attribute name="color">black</xsl:attribute>
            <xsl:value-of select="name"/>
          </xsl:element>
        </xsl:if>
        <xsl:if test="payment[text()='false']">
          <xsl:element name="font">
            <xsl:attribute name="color">red</xsl:attribute>
              <xsl:text>[</xsl:text>
              <xsl:value-of select="name"/>
              <xsl:text>]</xsl:text>
          </xsl:element>
        </xsl:if>
    </td>
    <td><xsl:value-of select="dogs"/></td>
    <td><xsl:value-of select="club"/></td>
  </tr>
</xsl:template>

</xsl:stylesheet>
