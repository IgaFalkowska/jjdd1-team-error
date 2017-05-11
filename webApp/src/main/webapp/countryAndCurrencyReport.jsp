<%--
  Created by IntelliJ IDEA.
  User: Iga
  Date: 09.05.2017
  Time: 23:09
  To change this template use File | Settings | File Templates.
--%>
<%@ include file="headersAndStyle.jsp" %>
<%@ include file="headerOptions.jsp" %>

<script src="vendor/Chart.bundle.js"></script>

<div class="row" style="padding-bottom: 120px; margin:0;">
        <div class="col-md-6" style="padding:20px 50px 0px;">

            <h3>Country report</h3>
            <canvas id="country" width="300" height="200"></canvas>

            <script>
                var ctx = document.getElementById("country");
                var country = new Chart(ctx, {
                    type: 'bar',
                    data: {
                        labels: ["Croatia", "France", "USA"],
                        datasets: [
                            {
                                label: "popularity of country",
                                backgroundColor: [
                                    'rgba(255, 99, 132, 0.2)',
                                    'rgba(54, 162, 235, 0.2)',
                                    'rgba(255, 206, 86, 0.2)',
                                    'rgba(75, 192, 192, 0.2)',
                                    'rgba(153, 102, 255, 0.2)',
                                    'rgba(255, 159, 64, 0.2)'
                                ],
                                borderColor: [
                                    'rgba(255,99,132,1)',
                                    'rgba(54, 162, 235, 1)',
                                    'rgba(255, 206, 86, 1)',
                                    'rgba(75, 192, 192, 1)',
                                    'rgba(153, 102, 255, 1)',
                                    'rgba(255, 159, 64, 1)'
                                ],
                                borderWidth: 1,
                                data: [65, 59, 80],
                            }
                        ]
                    }
                });
            </script>
        </div>

        <div class="col-md-6" style="padding:20px 50px 0px">

            <h3>Currency report</h3>
            <canvas id="currency" width="300" height="200"></canvas>

            <script>
                var ctx = document.getElementById("currency");
                var currency = new Chart(ctx, {
                    type: 'bar',
                    data: {
                        labels: ["Croatia", "France", "USA"],
                        datasets: [
                            {
                                label: "popularity of currency",
                                backgroundColor: [
                                    'rgba(255, 99, 132, 0.2)',
                                    'rgba(54, 162, 235, 0.2)',
                                    'rgba(255, 206, 86, 0.2)',
                                    'rgba(75, 192, 192, 0.2)',
                                    'rgba(153, 102, 255, 0.2)',
                                    'rgba(255, 159, 64, 0.2)'
                                ],
                                borderColor: [
                                    'rgba(255,99,132,1)',
                                    'rgba(54, 162, 235, 1)',
                                    'rgba(255, 206, 86, 1)',
                                    'rgba(75, 192, 192, 1)',
                                    'rgba(153, 102, 255, 1)',
                                    'rgba(255, 159, 64, 1)'
                                ],
                                borderWidth: 1,
                                data: [65, 59, 80],
                            }
                        ]
                    }
                });
            </script>
        </div>
</div>

<%@ include file="footer.jsp" %>
