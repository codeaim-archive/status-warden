<!DOCTYPE HTML>

<html>

<#include "../shared/head.ftl">

<body class="external-page external-alt sb-l-c sb-r-c">

<section id="content_wrapper">

        <section id="content">

            <div class="admin-form theme-info mw500" id="login">

                <!-- Login Logo -->
                <div class="row table-layout">
                    <a href="/dashboard" title="Return to Dashboard">
                        <img src="/assets/img/logos/logo.png" title="AdminDesigns Logo" class="center-block img-responsive" style="max-width: 275px;" />
                    </a>
                </div>

                <!-- Login Panel/Form -->
                <div class="panel mt30 mb25">

                    <form method="POST" action="/login" id="login-form">
                        <div class="panel-body bg-light p25 pb15">

                            <!-- Social Login Buttons -->
                            <div class="section row">
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
                                <div class="col-md-6 hidden">
                                    <a href="#" class="button btn-social twitter span-left btn-block">
                      <span>
                        <i class="fa fa-twitter"></i>
                      </span>Twitter</a>
                                </div>
                            </div>

                            <!-- Divider -->
                            <div class="section-divider mv30">
                                <span>OR</span>
                            </div>

                            <!-- Username Input -->
                            <div class="section">
                                <label for="username" class="field-label text-muted fs18 mb10">Email</label>
                                <label for="username" class="field prepend-icon">
                                    <input type="text" name="username" id="username" class="gui-input" placeholder="Enter email" />
                                    <label for="username" class="field-icon">
                                        <i class="fa fa-user"></i>
                                    </label>
                                </label>
                            </div>

                            <!-- Password Input -->
                            <div class="section">
                                <label for="password" class="field-label text-muted fs18 mb10">Password</label>
                                <label for="password" class="field prepend-icon">
                                    <input type="password" name="password" id="password" class="gui-input" placeholder="Enter password" />
                                    <label for="password" class="field-icon">
                                        <i class="fa fa-lock"></i>
                                    </label>
                                </label>
                            </div>

                        </div>

                        <div class="panel-footer clearfix">
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                            <button type="submit" class="button btn-primary btn-block">Sign In</button>
                        </div>

                    </form>
                </div>

                <!-- Registration Links -->
                <div class="login-links">
                    <p>
                        <a href="/forgot-password" class="active" title="Forgot password">Forgot password?</a>
                    </p>
                    <p>Don't have an account?
                        <a href="/register" class="active" title="Register">Register here</a>
                    </p>
                </div>

            </div>

        </section>
        <!-- end: .tray-center -->
</section>

<#include "../shared/scripts.ftl">

</body>

</html>