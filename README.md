
<div align="center">
  <img src="src/main/resources/images/meli.png" alt="logo meli"/>
</div>


# Projeto Integrador - Grupo 08 - Requisito 06 Heitor


#### <h2>Get Total Sales By Warehouse with Optional Filters Between Two Dates(Initial and Final)</h2>

<h3>Default endpoint</h3>

```http
  GET localhost:8080/api/v1/fresh-products/orders/totalsales?warehousecode={warehousecode}&initialdate={initialdate}&finaldate={finaldate}
```

<p>If you pass only initial date the endpoint will be filtered from initial date to current date</p>

| Param    | Type     | Description                                                                                      |
|:-------------|:---------|:-----------------------------------------------------------------------------------------------|
| `warehousecode` | `Long`    | **Required**. Warehouse code - example: 1
| `initialdate`   | `LocalDate` | **Optional**. Initial Date (pattern : yyyy-MM-dd) - example: 2020-12-13                |
| `finaldate`      | `LocalDate` | **Optional**. Final Date (pattern : yyyy-MM-dd) - example: 2022-12-31   |


## :file_folder: Download Endpoints

**Requisito 06 added in the collection:**
- [Collection (endpoints)](src/main/resources/projeto-integrador.postman_collection.json)

## :file_folder: PDF

- [Requisito 06 (pdf)](https://github.com/natalia-ssouzaml/integrative-project/blob/feat/general-data-analysis-endpoints/Requisito6-Heitor.pdf)
