<!doctype html>
<html class="login-pf" lang="en">
<head>
	<meta charset="utf-8">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>${properties.title}</title>
	<link rel="icon" href="/${resourcesPath}/img/favicon.ico" />
	<#if properties.stylesCommon?has_content>
		<#list properties.stylesCommon?split(' ') as style>
		<link href="/${resourcesCommonPath}/${style}" rel="stylesheet" />
		</#list>
	</#if>
	<#if properties.styles?has_content>
		<#list properties.styles?split(' ') as style>
		<link href="/${resourcesPath}/${style}" rel="stylesheet" />
		</#list>
	</#if>
	<#if properties.scripts?has_content>
		<#list properties.scripts?split(' ') as script>
	  <script src="/${resourcesPath}/${script}" type="text/javascript"></script>
		</#list>
	</#if>
</head>
<body>
	<div class="pf-v5-c-login">
	<div class="pf-v5-c-login__container">
		<header class="pf-v5-c-login__header">
			<div class="pf-v5-c-brand">
				<div class="logo-text">
					<span>iJUG e. V.</span>
				</div>
			</div>
		</header>
		<main class="pf-v5-c-login__main">
			<div class="pf-v5-c-login__main-header">
				<h1 class="pf-v5-c-title pf-m-3xl" id="kc-page-title">Anmeldung</h1>
			</div>
			<div class="pf-v5-c-login__main-body">
				<div class="pf-v5-c-login__main-footer">
					<div id="kc-social-providers" class="">
						<div class="pf-v5-c-form-control">
							<select id="login-select-toggle" onchange="selectIdP(this)">
								<option value="">Bitte wähle Deine JUG:</option>
								<#list jugs as p>
									<option value="${p.alias}" data-url="/realms/${p.alias}/account/">${p.displayName}</option>
								</#list>
							</select>
							<span class="pf-v5-c-form-control__utilities">
								<span class="pf-v5-c-form-control__toggle-icon">
									<svg class="pf-v5-svg" viewBox="0 0 320 512" fill="currentColor" aria-hidden="true" role="img" width="1em" height="1em">
										<path d="M31.3 192h257.3c17.8 0 26.7 21.5 14.1 34.1L174.1 354.8c-7.8 7.8-20.5 7.8-28.3 0L17.2 226.1C4.6 213.5 13.5 192 31.3 192z"></path>
									</svg>
								</span>
							</span>
						</div>
						<a id="btnLogin" href="#" class="pf-v5-c-button pf-m-primary pf-m-block pf-m-disabled">Anmelden</a>
					</div>
				</div>
			</div>
			<div class="pf-v5-c-login__main-footer">
				<div class="pf-v5-c-login__main-footer-band footer">
					<a class="pf-v5-c-login__main-footer-band-item pf-v5-u-font-size-sm pf-v5-u-color-200" href="https://www.ijug.eu" target="_blank">
						&copy; iJUG Interessenverbund der Java User Groups e.V.
					</a>
				</div>
			</div>
		</main>
	</div>
	</div>
</body>
</html>
