<#macro show social>
	<div id="kc-social-providers" class="${properties.kcFormSocialAccountSectionClass!}">
		<div class="${properties.kcInputClass!}">
			<#--noinspection HtmlFormInputWithoutLabel-->
			<select id="jug-select" onchange="selectIdP(this)">
				<option value="">${kcSanitize(msg("identity-provider-login-label"))}</option>
				<#list social.providers as p>
					<option value="${p.alias}" data-url="${p.loginUrl}">
						${p.displayName}
					</option>
				</#list>
			</select>
			<span class="${properties.kcFormControlUtilClass}">
				<span class="${properties.kcFormControlToggleIcon!}">
					<svg class="pf-v5-svg" viewBox="0 0 320 512" fill="currentColor" aria-hidden="true" role="img" width="1em" height="1em">
						<path d="M31.3 192h257.3c17.8 0 26.7 21.5 14.1 34.1L174.1 354.8c-7.8 7.8-20.5 7.8-28.3 0L17.2 226.1C4.6 213.5 13.5 192 31.3 192z"/>
					</svg>
				</span>
			</span>
		</div>
		<@buttons.buttonLink href="#" id="btnLogin" class=["kcButtonPrimaryClass", "kcButtonBlockClass", "kcButtonDisabledClass"] label="doLogIn"/>
	</div>
</#macro>
