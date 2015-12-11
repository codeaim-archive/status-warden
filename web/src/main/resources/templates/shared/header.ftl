<header class="navbar navbar-fixed-top bg-primary">
    <div class="navbar-branding">
        <a class="navbar-brand" href="/dashboard">
            StatusWarden
        </a>
        <span id="toggle_sidemenu_l" class="fa fa-bars"></span>
    </div>

    <ul class="nav navbar-nav navbar-right">
        <li>
            <a href="#">
                <span class="fa fa-envelope fs18"></span>
                <span class="badge badge-hero badge-warning">2</span>
            </a>
        </li>
        <li class="dropdown">
            <a href="#" class="dropdown-toggle fw600 p15" data-toggle="dropdown">
                <img src="assets/img/avatars/5.jpg" alt="avatar" class="mw30 br64 mr15"/> John.Smith
                <span class="caret caret-tp hidden-xs"></span>
            </a>
            <ul class="dropdown-menu list-group dropdown-persist w250" role="menu">
                <li class="list-group-item">
                    <a href="#">
                        <span class="fa fa-envelope"></span> Messages
                        <span class="label label-warning">2</span>
                    </a>
                </li>
                <li class="list-group-item">
                    <a href="#">
                        <span class="fa fa-cogs"></span> Settings </a>
                </li>
                <li class="list-group-item">
                    <form method="post" action="/logout" id="logout-header-form">
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                            <button type="submit" class="btn btn-link"><span class="fa fa-power-off"></span> Log out</button>
                    </form>
                </li>
            </ul>
        </li>
    </ul>
</header>