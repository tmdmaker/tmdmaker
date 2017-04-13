<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:exsl="http://exslt.org/common" version="1.0"
	exclude-result-prefixes="exsl">

	<xsl:import href="urn:docbkx:stylesheet" />
	<xsl:output method="html" encoding="UTF-8" indent="yes"
		doctype-public="-//W3C//DTD HTML 4.01 Transitional//EN"
		doctype-system="http://www.w3.org/TR/html4/loose.dtd" />


	<xsl:param name="use.extensions" select="1" />
	<xsl:param name="chapter.autolabel" select="1" />
	<xsl:param name="section.autolabel" select="1" />
	<xsl:param name="admon.graphics" select="1" />

	<xsl:template name="user.footer.content">
<script>
  (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
  (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
  m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
  })(window,document,'script','https://www.google-analytics.com/analytics.js','ga');

  ga('create', 'UA-96558945-1', 'auto');
  ga('send', 'pageview');

</script>

		<div style="border: 1px solid #9cf; padding: .5em;">
			<div>
				<a href="http://sourceforge.jp/">
					<img width="210" height="63" border="0" alt="SourceForge.JP">
						<xsl:attribute name="src">http://osdn.jp/sflogo.php?group_id=4743<![CDATA[&]]>type=3</xsl:attribute>
					</img>
				</a>
			</div>
		</div>
	</xsl:template>

</xsl:stylesheet>
