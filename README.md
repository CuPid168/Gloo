# Demo Gloo Computer Vision Model
## Setup Virtual Environment

This model can classify three Indonesian Packaged products : 

- Better Biscuit
- Pocari Sweat
- YouC1000 500ml

1. **Setup Environment Anaconda**:
    ```bash
    conda create --name main-ds python=3.9
    conda activate main-ds
    pip install -r requirements.txt
    ```

2. **Setup Environment - Shell/Terminal**:
    ```bash
    mkdir gloo-model-demo
    cd gloo-model-demo
    pipenv install
    pipenv shell
    pip install -r requirements.txt
    ```

3. **Run the application**:
    ```bash
    streamlit run main.py
    ```
