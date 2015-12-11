<!DOCTYPE HTML>

<html>

<#include "../shared/head.ftl">

<body>

<#include "../shared/header.ftl">
<#include "../shared/sidebar_left.ftl">

<section id="content_wrapper">
<#include "../shared/topbar.ftl">

    <!-- Begin: Content -->
    <section id="content" class="table-layout animated fadeIn">

        <!-- begin: .tray-center -->
        <div class="tray tray-center">

            <!-- recent activity table -->
            <div class="panel">
                <div class="panel-heading">
                    <span class="panel-title"> Monitors</span>
                </div>
                <div class="panel-body pn">
                    <div class="table-responsive">
                        <table class="table admin-form theme-warning tc-checkbox-1 fs13">
                            <thead>
                            <tr class="bg-light">
                                <th class="">Name</th>
                                <th class="">URL</th>
                                <th class="">Status</th>
                                <th class="">State</th>
                                <th class="">Last Updated</th>
                                <th class="">Check Interval</th>
                                <th class="">Uptime</th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr>
                            <#list monitors as monitor>
                                <td>${monitor.name!"Monitor Name"}</td>
                                <td>${monitor.url!"Monitor URL"}</td>
                                <td>${monitor.status!"Monitor Name"}</td>
                                <td>${monitor.state!"Monitor State"}</td>
                                <td>${monitor.updated!"Monitor Last Updated"}</td>
                                <td>${monitor.interval!"Monitor Check Interval"}</td>
                                <td>${monitor.uptime!"Monitor Uptime"}</td>
                            </#list>
                            </tr>
                            <tr>
                                <td class="">Google Monitor</td>
                                <td class="">http://www.google.com</td>
                                <td class="">Up</td>
                                <td class="">Waiting</td>
                                <td class="">27/10/2015 17:23</td>
                                <td class="">5 minutes</td>
                                <td class="">100%</td>
                            </tr>
                            <tr>
                                <td class="">Facebook Monitor</td>
                                <td class="">http://www.facebook.com</td>
                                <td class="">Up</td>
                                <td class="">Waiting</td>
                                <td class="">26/08/2015 09:23</td>
                                <td class="">1 minute</td>
                                <td class="">92%</td>
                            </tr>
                            <tr>
                                <td class="">Yahoo Monitor</td>
                                <td class="">http://www.yahoo.com</td>
                                <td class="">Down</td>
                                <td class="">Waiting</td>
                                <td class="">27/10/2015 16:23</td>
                                <td class="">3 minutes</td>
                                <td class="">47%</td>
                            </tr>
                            <tr>
                                <td class="">Amazon Monitor</td>
                                <td class="">http://www.amazon.com</td>
                                <td class="">Up</td>
                                <td class="">Waiting</td>
                                <td class="">27/10/2015 17:25</td>
                                <td class="">10 minutes</td>
                                <td class="">100%</td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>

        </div>
        <!-- end: .tray-center -->

    </section>
    <!-- End: Content -->

<#include "../shared/footer.ftl">
</section>

<#include "../shared/sidebar_right.ftl">
<#include "../shared/scripts.ftl">

</body>

</html>