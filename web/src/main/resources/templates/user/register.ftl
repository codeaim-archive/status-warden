<!DOCTYPE HTML>

<html>

<#include "../shared/head.ftl">

<body class="external-page external-alt sb-l-c sb-r-c">

<section id="content_wrapper">
    <section id="content">

        <div class="admin-form theme-primary mw600" style="margin-top: 3%;" id="register">

            <!-- Registration Logo -->
            <div class="row table-layout">
                <a href="/dashboard" title="Return to Dashboard">
                    <img src="/assets/img/logos/logo.png" title="AdminDesigns Logo" class="center-block img-responsive" style="max-width: 275px;"/>
                </a>
            </div>

            <!-- Registration Panel/Form -->
            <div class="panel mt40">

                <form method="post" action="/register" id="register-form" class="admin-form">
                    <div class="panel-body bg-light p25 pb15">


                        <!-- Social Login Buttons -->
                        <div class="section row hidden">
                            <div class="col-md-4">
                                <a href="#" class="button btn-social facebook span-left btn-block">
                      <span>
                        <i class="fa fa-facebook"></i>
                      </span>Facebook</a>
                            </div>
                            <div class="col-md-4">
                                <a href="#" class="button btn-social twitter span-left btn-block">
                      <span>
                        <i class="fa fa-twitter"></i>
                      </span>Twitter</a>
                            </div>
                            <div class="col-md-4">
                                <a href="#" class="button btn-social googleplus span-left btn-block">
                      <span>
                        <i class="fa fa-google-plus"></i>
                      </span>Google+</a>
                            </div>
                        </div>


                        <!-- Divider -->
                        <div class="section-divider mv30 hidden">
                            <span>OR</span>
                        </div>

                        <#--<div th:if="${#fields.hasAnyErrors()}" class="alert alert-danger pastel" >-->
                            <!--<button type="button" class="close" data-dismiss="alert" aria-hidden="true">x</button>-->
                            <!--Invalid registration details, please try again..-->
                        <#--</div>-->

                        <div class="section row">
                            <div class="col-md-12">
                                <label for="name" class="field prepend-icon">
                                    <input type="text" name="name" id="name" class="gui-input" placeholder="Name..."/>
                                    <label for="name" class="field-icon">
                                        <i class="fa fa-user"></i>
                                    </label>
                                </label>
                            </div>
                            <!-- end section -->
                        </div>
                        <!-- end .section row section -->

                        <div class="section">
                            <label for="email" class="control-label field prepend-icon">
                                <input type="email" name="email" id="email" class="gui-input state-error" placeholder="Email address"/>
                                <label for="email" class="field-icon">
                                    <i class="fa fa-envelope"></i>
                                </label>
                                <em class="state-error">fgfdgdfg</em>
                            </label>
                        </div>
                        <div class="section row" id="spy1">
                            <div class="col-md-6">
                                <label for="firstname" class="field prepend-icon state-error">
                                    <input type="text" name="firstname" id="firstname" class="gui-input" placeholder="First name..." aria-required="true">
                                    <label for="firstname" class="field-icon">
                                        <i class="fa fa-user"></i>
                                    </label>
                                </label><em for="firstname" class="state-error">Enter first name</em>
                            </div>
                            <!-- end section -->

                            <div class="col-md-6">
                                <label for="lastname" class="field prepend-icon state-error">
                                    <input type="text" name="lastname" id="lastname" class="gui-input" placeholder="Last name..." aria-required="true">
                                    <label for="lastname" class="field-icon">
                                        <i class="fa fa-user"></i>
                                    </label>
                                </label><em for="lastname" class="state-error">Enter last name</em>
                            </div>
                            <!-- end section -->
                        </div>
                        <!-- end section -->

                        <div class="section hidden">
                            <div class="smart-widget sm-right smr-120">
                                <label for="username" class="field prepend-icon">
                                    <input type="text" name="username" id="username" class="gui-input"
                                           placeholder="Choose your username"/>
                                    <label for="username" class="field-icon">
                                        <i class="fa fa-user"></i>
                                    </label>
                                </label>
                                <label for="username" class="button">.envato.com</label>
                            </div>
                            <!-- end .smart-widget section -->
                        </div>
                        <!-- end section -->


                        <hr class="alt short"/>

                        <div class="section">
                            <label for="password" class="field prepend-icon">
                                <input type="password" name="password" id="password" class="gui-input"
                                       placeholder="Create a password"/>
                                <label for="password" class="field-icon">
                                    <i class="fa fa-unlock-alt"></i>
                                </label>
                            </label>
                        </div>
                        <!-- end section -->

                        <div class="section">
                            <label for="confirmPassword" class="field prepend-icon">
                                <input type="password" name="confirmPassword" id="confirmPassword" class="gui-input"
                                       placeholder="Retype your password"/>
                                <label for="confirmPassword" class="field-icon">
                                    <i class="fa fa-lock"></i>
                                </label>
                            </label>
                        </div>
                        <!-- end section -->


                    </div>

                    <div class="panel-footer clearfix">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                        <button type="submit" class="button btn-primary mr10 pull-right">Register</button>

                        <label class="option block mt10">
                            <input type="checkbox" name="terms"/>
                            <span class="checkbox"></span>I agree to the
                            <a href="#" class="theme-link"> terms of use. </a>
                        </label>

                        <label class="switch block switch-primary mt10 hidden">
                            <input type="checkbox" name="remember" id="remember" checked="checked"/>
                            <label for="remember" data-on="YES" data-off="NO"></label>
                            <span>Remember me</span>
                        </label>
                    </div>

                </form>
            </div>

            <!--  Divider -->
            <hr class="alt mt40 mb30 mh70"/>

            <!-- Registration Social Links -->
            <div class="section row center-block" style="width: 550px;">
                <div class="col-md-6">
                    <a href="#" class="button btn-social facebook span-left btn-block">
                <span>
                  <i class="fa fa-facebook"></i>
                </span>Facebook</a>
                </div>
                <div class="col-md-6">
                    <a href="#" class="button btn-social googleplus span-left btn-block">
                <span>
                  <i class="fa fa-google-plus"></i>
                </span>Google+</a>
                </div>
            </div>

            <!-- Registration Links -->
            <div class="login-links">
                <p>
                    <a href="forgot-password" class="active" title="Forgot password">Forgot password?</a>
                </p>
                <p>Already registered?
                    <a href="login" class="active" title="Login">Login here</a>
                </p>
            </div>

        </div>

    </section>
</section>

<#include "../shared/scripts.ftl">

</body>

</html>