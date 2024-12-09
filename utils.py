import tensorflow as tf
import numpy as np
import streamlit as st

# Define the class names (labels)
classes = {
    0: 'Better',
    1: 'LeMinerale',
    2: 'Oreo',
    3: 'Pocari Sweat',
    4: 'YouC1000'
}

# Model utils
@st.cache_resource
def load_saved_model(model_path):
    """
    Load a TensorFlow SavedModel from the specified path.
    """
    model = tf.saved_model.load(model_path)
    return model

def predict_image_saved_model(model, image):
    """
    Predict the class of an image using a TensorFlow SavedModel.
    Assumes the model output is already softmaxed.
    """
    # Get the serving signature of the model
    infer = model.signatures["serving_default"]
    
    prediction = infer(tf.convert_to_tensor(image)) 

    prediction = prediction['output_0']  
    
    prediction = prediction.numpy()  

    # Get the predicted class index and label
    predicted_class = np.argmax(prediction, axis=-1)[0]
    prediction_str = classes[predicted_class]

    confidence = prediction[0, predicted_class] * 100

    return prediction_str, confidence

# Image utils
def preprocess_image(image, target_size=(224, 224)):
    """
    Preprocess the image to match the input shape of the TensorFlow SavedModel.
    """
    # Resize image to the target size
    image = image.resize(target_size)

    # Convert the image to a NumPy array and normalize
    image = np.asarray(image).astype('float32') / 255.0  # Normalize image
    image = np.expand_dims(image, axis=0)  # Add batch dimension

    return image