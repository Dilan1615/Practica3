from flask import Blueprint, abort, request, render_template, redirect, flash
import requests  # Aquí se importa el módulo requests para hacer solicitudes HTTP externas
import json

router = Blueprint('router', __name__)


@router.route('/admin/proyecto/register')
def view_register_proyecto():
    r = requests.get("http://localhost:8099/api/proyecto/listType")
    data =r.json()
    print(r.json())
    return render_template('proyecto/registro.html',lista = data["data"])


@router.route('/admin/proyecto/list')
def list_proyecto():
    
    # Rconeccion a la api
    r = requests.get("http://localhost:8099/api/proyecto/list")
    data = r.json()
    proyectos = data.get("data", [])
    
    # proyectos
    return render_template('lista.html', lista=proyectos)




@router.route('/')
def home():
   
    return render_template('proyecto/admin.html')

@router.route('/admin/proyecto/edit/<id>')
def view_edit_proyecto(id):
    r = requests.get("http://localhost:8099/api/proyecto/listType")
    data = r.json()  # Agregar paréntesis para llamar la función
    r1 = requests.get(f"http://localhost:8099/api/proyecto/get/{id}")
    data1 = r1.json()  # Agregar paréntesis para llamar la función
    if r1.status_code == 200:
        return render_template('proyecto/editar.html', lista=data["data"], proyecto=data1["data"])
    else:
        flash(data=["data"], category='error')
        return redirect('/admin/proyecto/list')


@router.route('/admin/proyecto/save', methods=['POST'])
def save_proyecto():
    headers = {'Content-Type': 'application/json'}
    form = request.form 
    dataF = {
        "nombre": form["nombre"],
        "inversionista": form["inversionista"],
        "inversion": form["inversion"],
        "tiempoVida": form["tiempoVida"],
        "inicio": form["inicio"],
        "fin": form["fin"],
        "tipo": form["tipo"]
    }
    r = requests.post('http://localhost:8099/api/proyecto/save', data=json.dumps(dataF), headers=headers)
    try:
        dat = r.json()
    except ValueError:
        dat = {}

    if r.status_code == 200:
        flash("Registro guardado correctamente", category='success')
        return redirect('/admin/proyecto/list')
    else:
        error_message = dat.get("data", "Error desconocido")
        flash(error_message, category='error')
        return redirect('/admin/proyecto/list')




@router.route('/admin/proyecto/update', methods=['POST'])
def update_proyecto():
    headers = {'Content-Type': 'application/json'}
    form = request.form
    dataF = {"id":form["id"],
        "nombre": form["nombre"],
        "inversionista": form["inversionista"],
        "inversion": form["inversion"],
        "tiempoVida": form["tiempoVida"],
        "inicio": form["inicio"],
        "fin": form["fin"],
        "tipo": form["tipo"]
    }
    r = requests.post('http://localhost:8099/api/proyecto/update', data=json.dumps(dataF), headers=headers)
    try:
        dat = r.json()
    except ValueError:
        dat = {}

    if r.status_code == 200:
        flash("Proyecto actualizado correctamente", category='success')
        return redirect('/admin/proyecto/list')
    else:
        error_message = dat.get("data", "Error desconocido")
        flash(error_message, category='error')
        return redirect('/admin/proyecto/list')
    

@router.route('/proyecto/list/searchLinealNombre', methods=['GET'])
def search_lineal_nombre():
    texto = request.args.get('texto', '')  # Obtén el parámetro 'texto' de la consulta
    response = requests.get(f'http://localhost:8099/api/proyecto/list/searchLinealName/{texto}')
    data = response.json()
    return render_template('lista.html', lista=data["data"])


@router.route('/proyecto/list/searchLinealInversonista', methods=['GET'])
def search_lineal_inversionista():
    texto = request.args.get('texto', '')  # Obtén el parámetro 'texto' de la consulta
    response = requests.get(f'http://localhost:8099/api/proyecto/list/searchLinealInversor/{texto}')
    data = response.json()
    return render_template('lista.html', lista=data["data"])


@router.route('/proyecto/list/searchBinaryInversonista', methods=['GET'])
def search_binary_inversionista():
    texto = request.args.get('texto', '')  # Obtén el parámetro 'texto' de la consulta
    response = requests.get(f'http://localhost:8099/api/proyecto/list/searchBinaryInversor/{texto}')
    data = response.json()
    return render_template('lista.html', lista=data["data"])

@router.route('/proyecto/list/searchBinaryName', methods=['GET'])
def search_binary_name():
    texto = request.args.get('texto', '')  # Obtén el parámetro 'texto' de la consulta
    response = requests.get(f'http://localhost:8099/api/proyecto/list/searchBinaryName/{texto}')
    data = response.json()
    return render_template('lista.html', lista=data["data"])

@router.route('/proyecto/list/orderShell/<attribute>/<type>', methods=['GET'])
def order_shell(attribute, type):
    response = requests.get(f'http://localhost:8099/api/proyecto/list/orderShell/{attribute}/{type}')
    proyectos = response.json().get('data', [])
    return render_template('lista.html', lista=proyectos)

@router.route('/proyecto/list/orderQuick/<attribute>/<type>', methods=['GET'])
def order_quick(attribute, type):
    response = requests.get(f'http://localhost:8099/api/proyecto/list/orderQuick/{attribute}/{type}')
    proyectos = response.json().get('data', [])
    return render_template('lista.html', lista=proyectos)

@router.route('/proyecto/list/orderMerge/<attribute>/<type>', methods=['GET'])
def order_merge(attribute, type):
    response = requests.get(f'http://localhost:8099/api/proyecto/list/orderMerge/{attribute}/{type}')
    proyectos = response.json().get('data', [])
    return render_template('lista.html', lista=proyectos)
