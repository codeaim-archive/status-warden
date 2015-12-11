<!DOCTYPE HTML>

<html>

<#include "../shared/head.ftl">

<body class="external-page external-alt sb-l-c sb-r-c">

<section id="content_wrapper">

        <section id="content">

            <div class="admin-form theme-info mw500" id="forgot-password">

                <!-- Login Logo -->
                <div class="row table-layout">
                    <a href="dashboard.html" title="Return to Dashboard">
                        <img src="assets/img/logos/logo.png" title="AdminDesigns Logo" class="center-block img-responsive" style="max-width: 275px;" />
                    </a>
                </div>

                <!-- Login Panel/Form -->
                <div class="panel mt30 mb25">

                    <form method="post" action="/" id="forgot-password-form">
                        <div class="panel-body bg-light p25 pb15">
                            <!-- Username Input -->
                            <div class="section">
                                <label for="email" class="field-label text-muted fs18 mb10">Email</label>
                                <label for="email" class="field prepend-icon">
                                    <input type="text" name="email" id="email" class="gui-input" placeholder="Enter email" />
                                    <label for="email" class="field-icon">
                                        <i class="fa fa-user"></i>
                                    </label>
                                </label>
                            </div>

                        </div>

                        <div class="panel-footer clearfix">
                            <button type="submit" class="button btn-primary btn-block">Forgot password</button>
                        </div>

                    </form>
                </div>

                <!-- Registration Links -->
                <div class="login-links">
                    <p>Already registered?
                        <a href="login" class="active" title="Sign In">Login here</a>
                    </p>
                    <p>Don't have an account?
                        <a href="register" class="active" title="Register">Register here</a>
                    </p>
                </div>

            </div>

        </section>
        <!-- end: .tray-center -->
</section>

<#include "../shared/scripts.ftl">

</body>

</html>