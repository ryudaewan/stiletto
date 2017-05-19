<%@ page contentType="text/html; charset=UTF-8" %><html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <style>
        #autocomplete {
            width: 150px;
            height: 200px;
            background-color: yellow;
            z-index: 3;
            position: relative;
            top: -19px;
            left: 0px;
            display: none;
        }
    </style>
    <script type="text/javascirpt">
        window.onload = function() {
            var searchKeyword = document.getElementById("searchKeyword");
            var autocomplete = document.getElementById("autocomplete");
            var keyword = "";

            searchKeyword.onkeydown = function(ex) {
                searchKeyword.onkeyup(ex);
            };

            searchKeyword.onkeyup = function(ex) {
                if (ex.char) {
                    keyword = searchKeyword.value;
                }

                if (keyword == "") {
                    autocomplete.style.display="none";
                } else {
                    autocomplete.style.display="block";
                    fillSearchResult(autocomplete);
                }
            };
        };

        function fillSearchResult(autocomplet) {
            autocomplete.innerHTML = "";
        }

        function selectData(that) {
            var searchKeyword = document.getElementById("searchKeyword");
            var autocomplete = document.getElementById("autocomplete");
            searchKeyword.value = that.innerText;
            autocomplete.style.display = "none";
        }
    </script>
    <title>Title</title>
</head>
<body>
    <form action="${pageContext.request.contextPath}/searchCustomers" method="GET">
        고객사명 <input type="text" id="searchKeyword" name="keyword"/>
        <div id="autocomplete"></div>
        <input type="submit" value="발사"/>
    </form>
</body>
</html>